package com.example.simpleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.core.lookup
import com.example.modules.module.ModuleProxy

class MainActivity : AppCompatActivity() {
    private val proxy by lookup<ModuleProxy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(proxy.home.startFragment)
    }

    private fun showFragment(f: Fragment, args: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, f.apply { arguments = args })
            .commit()
    }

    fun showFragment(f: String, args: Bundle? = null) {
        try {
            showFragment(
                supportFragmentManager.fragmentFactory.instantiate(classLoader, f), args
            )
        } catch (e: Fragment.InstantiationException) {
            val message = e.message ?: "Not found fragment $f"
            Log.e("NotFound", e.message ?: "Not found fragment $f")
            Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show()
        }
    }
}

val Fragment.navigator: MainActivity
    get() = requireActivity() as MainActivity