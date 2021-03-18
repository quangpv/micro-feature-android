package com.example.home.features.config

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Chanel
import com.example.core.Mediator
import com.example.home.features.HomeCommand
import com.example.modules.configure.ConfigModel
import com.example.modules.module.ModuleProxy
import kotlinx.coroutines.launch

class LoadConfigViewModel(private val proxy: ModuleProxy) : ViewModel(), Mediator.Contract {

    val newConfig = MediatorLiveData<ConfigModel>()

    fun loadConfig() = viewModelScope.launch {
        newConfig.value = proxy.config.loadConfig()
    }

    override fun invoke(chanel: Chanel, command: Mediator.Command) {
        if (command is HomeCommand.LoggedIn) {
            viewModelScope.launch {
                val config = proxy.config.loadConfig()
                newConfig.value = object : ConfigModel {
                    override val value: String get() = "(${command.userName}) ${config.value}"
                }
            }
        }
    }
}