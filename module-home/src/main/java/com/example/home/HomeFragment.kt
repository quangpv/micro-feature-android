package com.example.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.core.lookup
import com.example.core.viewModel
import com.example.home.features.HomeFeature

class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        const val LOGGED = "logged"
    }

    val mediator: HomeViewModel by viewModel()
    private val features: List<HomeFeature> by lookup()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        features.forEach { it(this) }
    }
}