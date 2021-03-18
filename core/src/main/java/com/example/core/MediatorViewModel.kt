package com.example.core

import androidx.lifecycle.ViewModel

abstract class MediatorViewModel<T : Mediator> : ViewModel(), MediatorOwner<T> {
    final override lateinit var mediator: T
}