package com.example.home.features.config

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.launch
import com.example.core.viewBy
import com.example.home.HomeMediator
import com.example.home.R
import com.example.home.features.HomeFeature
import com.example.modules.configure.ConfigModel
import com.example.modules.module.ModuleProxy

class LoadConfigFeature : HomeFeature {

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: Fragment, mediator: HomeMediator) = block(fragment) {
        val viewModel = getViewModel<LoadConfigViewModel>()

        val btnLoadConfig = viewBy<TextView>(R.id.btnClickLoadConfig)
            .apply { visibility = View.VISIBLE }

        btnLoadConfig.setOnClickListener {
            viewModel.loadConfig()
        }

        viewModel.newConfig.observe(viewLifecycleOwner) {
            btnLoadConfig.text = "${btnLoadConfig.text} ${it.value}"
        }

        mediator.collectForm.observe(viewLifecycleOwner) {
            it += ConfigPart(viewModel.newConfig.value?.value ?: "")
        }

        mediator.loggedIn.observe(viewLifecycleOwner) {
            btnLoadConfig.text = "(Logged In) ${btnLoadConfig.text}"
        }

        mediator.loggedOut.observe(viewLifecycleOwner) {
            btnLoadConfig.text = "Click to load config"
        }

    }
}

class LoadConfigViewModel(private val proxy: ModuleProxy) : ViewModel() {

    val newConfig = MediatorLiveData<ConfigModel>()

    fun loadConfig() = launch {
        newConfig.value = proxy.config.loadConfig()
    }
}