package com.example.core

import androidx.lifecycle.LiveData

interface StatusWindowOwner {
    val error: LiveData<out Throwable>
    val loading: LiveData<Boolean>
}