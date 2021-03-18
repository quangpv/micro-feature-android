package com.example.home.features.logout

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modules.authenticate.AuthenticateProxy
import kotlinx.coroutines.launch

class LogoutViewModel(private val proxy: AuthenticateProxy) : ViewModel() {

    val logout = MediatorLiveData<Any>()

    fun logout() {
        viewModelScope.launch {
            proxy.logout()
            logout.value = Any()
        }
    }
}