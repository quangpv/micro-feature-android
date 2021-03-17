package com.example.core

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(scope: CoroutineScope, function: (T) -> Unit) {
    scope.launch {
        collect {
            function(it)
        }
    }
}

fun View.view(id: Int): View {
    return findViewById(id)
}

fun Fragment.view(id: Int): View {
    return requireView().findViewById(id)
}

fun <T : View> View.viewBy(id: Int): T {
    return findViewById(id)
}

fun <T : View> Fragment.viewBy(id: Int): T {
    return requireView().findViewById(id)
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(): Lazy<T> = lazy {
    ViewModelProvider(this)[T::class.java]
}

fun <T> block(t: T, function: T.() -> Unit) {
    function(t)
}