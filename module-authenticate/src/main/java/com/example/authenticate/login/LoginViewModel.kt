package com.example.authenticate.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authenticate.AuthorRepository
import com.example.core.lookup
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepo: AuthorRepository by lookup()

    val loginSuccess = MutableLiveData<Any>()

    fun login(userName: String) {
        viewModelScope.launch {
            authRepo.login(userName)
            loginSuccess.value = userName
        }
    }
}