package com.example.home.features.login

import com.example.core.Mediator
import com.example.home.features.HomeCommand
import com.example.modules.authenticate.UserModel
import com.example.modules.module.ModuleProxy

class LoginContract(private val proxy: ModuleProxy) : Mediator.Contract {

    class CheckAuth : Mediator.Command

    class LoggedIn(
        override val userName: String,
        override val isLogged: Boolean
    ) : HomeCommand.LoggedIn

    override suspend fun invoke(command: Mediator.Command) = when (command) {
        is CheckAuth -> {
            val user = proxy.authenticate.getUserLoggedIn()
            throw RuntimeException("Not found")
            LoggedIn(user.userName, user !is UserModel.Empty)
        }
        else -> Mediator.Empty
    }
}
