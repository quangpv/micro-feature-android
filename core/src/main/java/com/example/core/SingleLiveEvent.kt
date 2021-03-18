package com.example.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class SingleLiveEvent<T> : CommandLiveData<T>() {
    private var mNotified = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, object : Observer<T> {
            override fun onChanged(t: T) {
                if (mNotified) return
                observer.onChanged(t)
                mNotified = true
            }
        })
    }

    override fun setValue(value: T?) {
        mNotified = false
        super.setValue(value)
    }
}