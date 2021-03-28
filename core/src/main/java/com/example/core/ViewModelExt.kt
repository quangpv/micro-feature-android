package com.example.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
    try {
        block()
    } catch (e: CancellationException) {
        Log.e("Cancel", e.message ?: "Unknown")
    } catch (e: InterruptedException) {
        Log.e("Interrupted", e.message ?: "Unknown")
    } catch (e: Throwable) {
        Log.e("Launch", e.message ?: "Unknown")
    }
}