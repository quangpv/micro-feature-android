package com.example.home.features.config

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.core.block
import com.example.core.observe
import com.example.core.viewBy
import com.example.home.HomeFragment
import com.example.home.R
import com.example.home.features.HomeCommand
import com.example.home.features.HomeFeature

class LoadConfigFeature : HomeFeature {

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: HomeFragment) = block(fragment) {
        mediator.add(LoadConfigContract::class)

        val btnLoadConfig = viewBy<TextView>(R.id.btnClickLoadConfig)
            .apply { visibility = View.VISIBLE }

        btnLoadConfig.setOnClickListener {
            mediator.send(LoadConfigContract.Load)
        }

        mediator.observe<LoadConfigContract.NewConfig>(viewLifecycleOwner) {
            btnLoadConfig.text = "${btnLoadConfig.text} ${config.value}"
        }

        mediator.observe<HomeCommand.Collect>(viewLifecycleOwner) {
            payload["config"] = "config"
        }

        mediator.observe<HomeCommand.LoggedIn>(viewLifecycleOwner) {
            if (isLogged) btnLoadConfig.text = "(Logged In) ${btnLoadConfig.text}"
        }
    }
}