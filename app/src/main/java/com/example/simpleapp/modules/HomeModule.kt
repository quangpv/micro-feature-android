package com.example.simpleapp.modules

import com.example.core.gatewayModule
import com.example.home.HomeGateway
import com.example.home.actions.GotoLoginAction
import com.example.home.homeModules
import com.example.modules.home.HomeProxy
import com.example.modules.module.ModuleAction
import com.example.modules.module.ModuleEvent
import com.example.simpleapp.navigator

val homeGateway = gatewayModule<HomeProxy>(homeModules) {
    HomeGateway(HomeCoordinator())
}

private class HomeCoordinator : ModuleEvent {
    companion object {
        const val LOGIN = "com.example.authenticate.login.LoginFragment"
    }

    override fun onEvent(action: ModuleAction) {
        when (action) {
            is GotoLoginAction -> action.fragment.navigator
                .showFragment(LOGIN)
        }
    }
}
