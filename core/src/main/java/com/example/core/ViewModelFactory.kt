package com.example.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory : ViewModelProvider.Factory {
    private val defaultFactory = ViewModelProvider.NewInstanceFactory()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            diContext[modelClass.kotlin]
        } catch (e: Throwable) {
            defaultFactory.create(modelClass)
        }
    }
}