package com.example.core

import android.app.Application
import android.content.Context
import kotlin.reflect.KClass

class Bean(private val function: () -> Any, private val single: Boolean) {
    private var mValue: Any? = null

    val value: Any
        get() {
            if (!single) return function()
            if (mValue == null) mValue = function()
            return mValue!!
        }
}

class DependencyContext {
    private var mCached = hashMapOf<String, Bean>()

    fun <T : Any> provide(clazz: KClass<out T>, single: Boolean, function: () -> T) {
        mCached[clazz.java.name] = Bean(function, single)
    }

    inline fun <reified T : Any> single(noinline function: () -> T) {
        provide(T::class, true, function)
    }

    inline fun <reified T : Any> factory(noinline function: () -> T) {
        provide(T::class, false, function)
    }

    fun modules(vararg modules: DependencyModule) {
        for (module in modules) {
            module(this)
        }
    }

    operator fun <T : Any> get(kClass: KClass<out T>): T {
        if (!mCached.containsKey(kClass.java.name)) error("Not found definition ${kClass.java.simpleName}")
        return mCached[kClass.java.name]!!.value as T
    }

    inline fun <reified T : Any> get(): T {
        val clazz = T::class
        return get(clazz)
    }
}

class DependencyModule(private val function: DependencyContext.() -> Unit) {
    operator fun invoke(context: DependencyContext) {
        function(context)
    }
}

fun module(function: DependencyContext.() -> Unit): DependencyModule {
    return DependencyModule(function)
}

inline fun <reified T : Any> proxyModule(
    diModule: DependencyModule? = null,
    noinline function: DependencyContext.() -> T
): DependencyModule {
    return module {
        single { function() }
        diModule?.apply { modules(this) }
    }
}

inline fun <reified T : Any> DependencyContext.proxyOf(vararg module: DependencyModule) {
    single { ProxyProvider[T::class] }
    modules(*module)
}

fun Application.provides(function: DependencyContext. () -> Unit) {
    diContext.single<Application> { this }
    diContext.single<Context> { this }
    function(diContext)
}

inline fun <reified T : Any> lookup() = lazy {
    diContext[T::class]
}

val diContext = DependencyContext()