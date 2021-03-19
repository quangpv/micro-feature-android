package com.example.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

interface Feature {
    operator fun invoke(fragment: Fragment)
}

interface MediatorFeature<T : Mediator> {
    operator fun invoke(fragment: Fragment, mediator: T)
}

interface ViewRegistrable {

    fun onDestroyView()

    fun <T : Fragment> onViewCreated(fragment: T?, function: T.() -> Unit) {
        val f = fragment ?: return
        f.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    onDestroyView()
                    source.lifecycle.removeObserver(this)
                }
            }
        })
        f.function()
    }
}