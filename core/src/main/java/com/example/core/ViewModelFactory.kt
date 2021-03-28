package com.example.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory : ViewModelProvider.Factory {
    private val defaultFactory = ViewModelProvider.NewInstanceFactory()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = try {
            diContext[modelClass.kotlin]
        } catch (e: Throwable) {
            try {
                defaultFactory.create(modelClass)
            } catch (t: Throwable) {
                throw e
            }
        }
        onCreate(viewModel)
        return viewModel
    }

    open fun onCreate(viewModel: ViewModel) {
        if (viewModel is Creatable) viewModel.onCreate()
    }
}