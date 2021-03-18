package com.example.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KClass

interface Mediator : Chanel {

    fun add(contract: Contract): Mediator

    fun <T : Command> observe(
        clazz: KClass<T>,
        owner: LifecycleOwner,
        function: MediatorObserver<T>
    )

    interface Command
    interface Collect : Command

    object Empty : Command

    interface Contract {
        operator fun invoke(chanel: Chanel, command: Command) {
            chanel.send(invoke(command))
        }

        operator fun invoke(command: Command): Command = Empty
    }
}

interface Chanel {
    fun send(command: Mediator.Command)
}

class MediatorDelegate : Mediator {
    private val mContracts = arrayListOf<Mediator.Contract>()
    private val broadcast = MutableLiveData<Mediator.Command>()

    override fun send(command: Mediator.Command) {
        if (command is Mediator.Empty) return

        if (command !is Mediator.Collect) for (mContract in mContracts) {
            mContract(this, command)
        }
        broadcast.value = command
    }

    override fun add(contract: Mediator.Contract): Mediator {
        for ((index, mContract) in mContracts.withIndex()) {
            if (mContract == contract) return this
            if (mContract.javaClass == contract.javaClass) {
                mContracts.removeAt(index)
                mContracts.add(index, contract)
                return this
            }
        }
        mContracts.add(contract)
        return this
    }

    override fun <T : Mediator.Command> observe(
        clazz: KClass<T>,
        owner: LifecycleOwner,
        function: MediatorObserver<T>
    ) {
        broadcast.observe(owner, {
            if (clazz.isInstance(it)) function(it as T)
        })
    }
}

inline fun <reified T : Mediator.Command> Mediator.observe(
    owner: LifecycleOwner,
    noinline function: MediatorObserver<T>
) {
    observe(T::class, owner, function)
}

typealias MediatorObserver<T> = T.() -> Unit