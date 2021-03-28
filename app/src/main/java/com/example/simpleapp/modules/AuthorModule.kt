package com.example.simpleapp.modules

import androidx.core.os.bundleOf
import com.example.authenticate.AuthenticateGateway
import com.example.authenticate.authenticateModules
import com.example.authenticate.login.LoginSuccessAction
import com.example.core.navigator
import com.example.core.proxyModule
import com.example.core.showFragment
import com.example.modules.authenticate.AuthenticateProxy
import com.example.modules.module.ModuleAction
import com.example.modules.module.ModuleEvent
import com.example.simpleapp.R

val authGateway = proxyModule<AuthenticateProxy>(authenticateModules) {
    AuthenticateGateway(AuthorCoordinator())
}

private class AuthorCoordinator : ModuleEvent {
    companion object {
        const val HOME = "com.example.home.HomeFragment"
        const val KEY_PARAMS = "logged"
    }

    override fun onEvent(action: ModuleAction) {
        when (action) {
            is LoginSuccessAction -> action.fragment.navigator.showFragment(
                R.id.containerView,
                HOME, bundleOf(KEY_PARAMS to action.userName)
            )
        }
    }
}