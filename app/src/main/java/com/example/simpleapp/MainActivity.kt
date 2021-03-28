package com.example.simpleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.lookup
import com.example.core.showFragment
import com.example.modules.module.ModuleProxy

class MainActivity : AppCompatActivity() {
    private val proxy by lookup<ModuleProxy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(R.id.containerView, proxy.home.startFragment)
    }
}