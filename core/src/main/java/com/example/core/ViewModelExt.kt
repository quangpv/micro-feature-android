package com.example.core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

fun ViewModel.launch(
    loading: LiveData<Boolean>? = (this as? StatusWindowOwner)?.loading
        ?: ((this as? MediatorOwner<*>)?.mediator as? StatusWindowOwner)?.loading,

    error: LiveData<out Throwable>? = (this as? StatusWindowOwner)?.error
        ?: ((this as? MediatorOwner<*>)?.mediator as? StatusWindowOwner)?.error,

    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    val mutableLoading = loading as? MutableLiveData
    try {
        mutableLoading?.value = true
        block()
    } catch (e: CancellationException) {
        Log.e("Cancel", e.message ?: "Unknown")
    } catch (e: InterruptedException) {
        Log.e("Interrupted", e.message ?: "Unknown")
    } catch (e: Throwable) {
        Log.e("Launch", e.message ?: "Unknown")
        (error as? MutableLiveData)?.value = e
    } finally {
        mutableLoading?.value = false
    }
}