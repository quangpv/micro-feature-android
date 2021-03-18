package com.example.home.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modules.module.ModuleProxy
import kotlinx.coroutines.launch

class LoginViewModel(private val proxy: ModuleProxy) : ViewModel() {

    val loggedIn = MutableLiveData<String>()

    fun checkAuth(userName: String) = viewModelScope.launch {
        val name = if (userName.isNotBlank()) userName
        else proxy.authenticate.getUserLoggedIn().userName

        loggedIn.value = name
    }
}
