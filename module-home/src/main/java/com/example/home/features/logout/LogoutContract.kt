package com.example.home.features.logout

import android.util.Log
import com.example.core.Chanel
import com.example.core.Mediator
import com.example.modules.authenticate.AuthenticateProxy

class LogoutContract(private val proxy: AuthenticateProxy) : Mediator.Contract {
    object Logout : Mediator.Command
    object BackToLogin : Mediator.Command

    class ShowLog(val payload: HashMap<String, Any>) : Mediator.Command

    override suspend fun invoke(chanel: Chanel, command: Mediator.Command) {
        if (command is Logout) {
            proxy.logout()
            chanel.send(BackToLogin)
        } else if (command is ShowLog) {
            Log.e("Hello", command.payload.entries.joinToString())
        }
    }
}