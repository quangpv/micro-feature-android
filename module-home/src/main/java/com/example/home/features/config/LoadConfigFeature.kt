package com.example.home.features.config

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.observe
import com.example.core.viewBy
import com.example.home.HomeFragment
import com.example.home.R
import com.example.home.features.HomeCommand
import com.example.home.features.HomeFeature

class LoadConfigFeature : HomeFeature {

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: HomeFragment) = block(fragment) {
        val viewModel = getViewModel<LoadConfigViewModel>()
        mediator.add(viewModel)

        val btnLoadConfig = viewBy<TextView>(R.id.btnClickLoadConfig)
            .apply { visibility = View.VISIBLE }

        btnLoadConfig.setOnClickListener {
            viewModel.loadConfig()
        }

        viewModel.newConfig.observe(viewLifecycleOwner) {
            btnLoadConfig.text = "${btnLoadConfig.text} ${it.value}"
        }

        mediator.observe<HomeCommand.Collect>(viewLifecycleOwner) {
            payload["config"] = "config"
        }

        mediator.observe<HomeCommand.LoggedIn>(viewLifecycleOwner) {
            btnLoadConfig.text = "(Logged In) ${btnLoadConfig.text}"
        }
    }
}