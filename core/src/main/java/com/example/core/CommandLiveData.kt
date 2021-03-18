package com.example.core

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData

open class CommandLiveData<T> : MutableLiveData<T>() {

    @SuppressLint("RestrictedApi")
    fun call() {
        if (ArchTaskExecutor.getInstance().isMainThread) value = null
        else postValue(null)
    }

    fun send(value: T): T {
        setValue(value)
        return value
    }
}