package com.example.simpleapp.modules.author

import androidx.core.os.bundleOf
import com.example.authenticate.login.LoginSuccessAction
import com.example.modules.module.ModuleAction
import com.example.modules.module.ModuleEvent
import com.example.simpleapp.navigator

class AuthorCoordinator : ModuleEvent {
    companion object {
        const val HOME = "com.example.home.HomeFragment"
        const val KEY_PARAMS = "logged"
    }

    override fun onEvent(action: ModuleAction) {
        when (action) {
            is LoginSuccessAction -> action.fragment.navigator.showFragment(
                HOME, bundleOf(KEY_PARAMS to action.userName)
            )
        }
    }
}