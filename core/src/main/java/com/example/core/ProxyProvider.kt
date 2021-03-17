package com.example.core

import java.lang.reflect.Proxy
import kotlin.reflect.KClass

@Suppress("unchecked_cast")
object ProxyProvider {
    operator fun <T : Any> get(clazz: KClass<T>): T {
        return Proxy.newProxyInstance(
            javaClass.classLoader, arrayOf(clazz.java)
        ) { _, method, _ ->
            val returnType = method.returnType.kotlin
            val returnValue: Any = try {
                diContext[returnType]
            } catch (e: Throwable) {
                createProxy(returnType).also {
                    diContext.provide(returnType, true) { it }
                }
            }
            returnValue
        } as T
    }

    private fun <T : Any> createProxy(proxyType: KClass<T>): T {
        return Proxy.newProxyInstance(
            javaClass.classLoader,
            arrayOf(proxyType.java)
        ) { _, method, _ ->
            throw NotImplementedError("${proxyType.java.simpleName} - ${method.name} Not implement yet")
        } as T
    }
}