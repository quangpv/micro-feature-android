package com.example.home.features.config

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.core.BodyPart
import com.example.core.MediatorOwner
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
        val viewModel = getViewModel<LoadConfigViewModel>(mediator)

        val btnLoadConfig = viewBy<TextView>(R.id.btnClickLoadConfig)
            .apply { visibility = View.VISIBLE }

        btnLoadConfig.setOnClickListener {
            viewModel.loadConfig()
        }

        viewModel.newConfig.observe(viewLifecycleOwner) {
            btnLoadConfig.text = it
        }

        mediator.loggedIn.observe(viewLifecycleOwner) {
            viewModel.setLoggedIn(true)
        }

        mediator.loggedOut.observe(viewLifecycleOwner) {
            viewModel.setLoggedIn(false)
        }

        mediator.collectForm.observe(viewLifecycleOwner) {
            it += viewModel.createPart()
        }
    }
}

class LoadConfigViewModel(private val proxy: ModuleProxy) : ViewModel(),
    MediatorOwner<HomeMediator> {
    override lateinit var mediator: HomeMediator

    private var loggedIn = false
    private var config: ConfigModel? = null

    val newConfig = MediatorLiveData<String>()

    fun loadConfig() = launch {
        config = proxy.config.loadConfig()
        updateChange()
    }

    fun setLoggedIn(b: Boolean) {
        loggedIn = b
        updateChange()
    }

    fun createPart(): BodyPart {
        return ConfigPart(config?.value ?: "")
    }

    private fun updateChange() {
        newConfig.value = when {
            loggedIn && config != null -> "(Logged In) ${config!!.value}"
            loggedIn -> "(Logged In) Click to load config"
            config != null -> "New config loaded: ${config!!.value}"
            else -> "Click to load config"
        }
    }
}