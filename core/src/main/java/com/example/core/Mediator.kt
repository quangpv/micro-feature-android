package com.example.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

interface Mediator {
    val loading: LiveData<Boolean>
    val error: LiveData<Throwable>

    fun send(
        command: Command,
        loading: LiveData<Boolean>? = DefaultLoading,
        error: LiveData<Throwable>? = DefaultError
    )

    fun add(contract: KClass<out Contract>): Mediator

    fun <T : Any> observe(clazz: KClass<T>, owner: LifecycleOwner, function: MediatorObserver<T>)

    interface Command
    interface Collect : Command

    object Empty : Command

    interface Contract {
        suspend operator fun invoke(chanel: Chanel, command: Command) {
            chanel.send(invoke(command))
        }

        suspend operator fun invoke(command: Command): Command = Empty
    }
}

object DefaultLoading : LiveData<Boolean>()
object DefaultError : LiveData<Throwable>()

interface Chanel {
    suspend fun send(command: Mediator.Command)
}

class MediatorDelegate : BaseMediator() {
    private var mScope: CoroutineScope? = null

    override val scope: CoroutineScope
        get() = mScope ?: error("Not set yet!")

    fun setScope(scope: CoroutineScope) {
        mScope = scope
        scope.produce<Any> {
            invokeOnClose {
                for (mObserver in mObservers) {
                    mObserver.clear()
                }
                mObservers.clear()
                mContracts.clear()
            }
        }
    }
}

class FeatureMediator(override val scope: CoroutineScope) : BaseMediator() {
    init {
        scope.produce<Any> {
            invokeOnClose {
                for (mObserver in mObservers) {
                    mObserver.clear()
                }
                mObservers.clear()
                mContracts.clear()
            }
        }
    }
}

abstract class BaseMediator : Mediator {

    abstract val scope: CoroutineScope
    protected val mObservers = arrayListOf<MediatorObserverWrapper<out Any>>()
    protected val mContracts = arrayListOf<Mediator.Contract>()
    private val self get() = this

    override val loading = MutableLiveData<Boolean>()
    override val error = MutableLiveData<Throwable>()

    private val chanel = object : Chanel {
        override suspend fun send(command: Mediator.Command) {
            broadcast(command)
        }
    }

    override fun add(contract: KClass<out Mediator.Contract>): Mediator {
        if (mContracts.find { contract.java.isInstance(it) } != null) return this
        mContracts.add(diContext[contract])
        return this
    }

    override fun send(
        command: Mediator.Command,
        loading: LiveData<Boolean>?,
        error: LiveData<Throwable>?
    ) {
        val blocking = command is Mediator.Collect

        if (blocking) runBlocking { broadcast(command, true) }
        else scope.launch {
            val loadingEvent = if (loading == DefaultLoading) self.loading else loading
            try {
                (loadingEvent as? MutableLiveData)?.postValue(true)
                broadcast(command)
            } catch (e: CancellationException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                val errorEvent = if (error == DefaultError) self.error else error
                (errorEvent as? MutableLiveData)?.postValue(e)
            } finally {
                (loadingEvent as? MutableLiveData)?.postValue(false)
            }
        }
    }

    suspend fun broadcast(command: Mediator.Command, blocking: Boolean = false) {
        if (command is Mediator.Empty) return

        if (!blocking) for (contract in mContracts) {
            contract(chanel, command)
        }
        if (mObservers.isEmpty()) return

        fun run() {
            for (mObserver in mObservers) {
                if (mObserver.accept(command)) {
                    @Suppress("unchecked_cast")
                    mObserver as MediatorObserverWrapper<Any>

                    mObserver.notify(command)
                }
            }
        }
        if (blocking) run() else withContext(Dispatchers.Main) {
            run()
        }
    }

    override fun <T : Any> observe(
        clazz: KClass<T>,
        owner: LifecycleOwner,
        function: MediatorObserver<T>
    ) {
        mObservers.add(MediatorObserverWrapper(function, owner, clazz))
    }

    protected inner class MediatorObserverWrapper<T : Any>(
        private val function: MediatorObserver<T>,
        owner: LifecycleOwner,
        private val kClass: KClass<T>
    ) {
        private var mCommand: T? = null
        private val lifecycle = owner.lifecycle

        private val isActive: Boolean
            get() = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
                && lifecycle.currentState != Lifecycle.State.DESTROYED

        private val mObserver = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_DESTROY -> {
                        source.lifecycle.removeObserver(this)
                        mObservers.remove(this@MediatorObserverWrapper)
                    }
                    Lifecycle.Event.ON_START -> {
                        if (mCommand != null) doNotify(mCommand!!)
                    }
                    else -> {
                    }
                }
            }
        }

        init {
            lifecycle.addObserver(mObserver)
        }

        fun clear() {
            lifecycle.removeObserver(mObserver)
            mCommand = null
        }

        fun notify(command: T) {
            if (isActive) doNotify(command)
            else mCommand = command
        }

        private fun doNotify(command: T) {
            function(command)
            mCommand = null
        }

        fun accept(command: Any): Boolean {
            return kClass.java.isInstance(command)
        }
    }
}

inline fun <reified T : Any> Mediator.observe(
    owner: LifecycleOwner,
    noinline function: MediatorObserver<T>
) {
    observe(T::class, owner, function)
}

typealias MediatorObserver<T> = T.() -> Unit