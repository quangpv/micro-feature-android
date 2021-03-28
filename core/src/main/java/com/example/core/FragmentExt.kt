package com.example.core

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.showFragment(containerId: Int, f: Fragment, args: Bundle? = null) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, f.apply { arguments = args })
        .commit()
}

fun FragmentActivity.showFragment(containerId: Int, f: String, args: Bundle? = null) {
    try {
        showFragment(
            containerId,
            supportFragmentManager.fragmentFactory.instantiate(classLoader, f), args
        )
    } catch (e: Fragment.InstantiationException) {
        val message = e.message ?: "Not found fragment $f"
        Log.e("NotFound", e.message ?: "Not found fragment $f")
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}

val Fragment.navigator: FragmentActivity
    get() = requireActivity()