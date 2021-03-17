package com.example.home.features.login

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.core.block
import com.example.core.lookup
import com.example.core.observe
import com.example.core.viewBy
import com.example.home.HomeEmitter
import com.example.home.HomeFragment
import com.example.home.R
import com.example.home.actions.GotoLoginAction
import com.example.home.features.HomeCommand
import com.example.home.features.HomeFeature

class LoginFeature : HomeFeature {
    private lateinit var btnLogin: View

    private val emitter: HomeEmitter by lookup()

    override fun invoke(fragment: HomeFragment) = block(fragment) {
        mediator.add(LoginContract::class)

        btnLogin = viewBy<View>(R.id.btnGotoLogin).apply { visibility = View.VISIBLE }

        val userName = arguments?.getString(HomeFragment.LOGGED) ?: ""

        btnLogin.setOnClickListener {
            emitter.emit(GotoLoginAction(this))
        }

        mediator.observe<LoginContract.LoggedIn>(viewLifecycleOwner) {
            btnLogin.visibility = if (isLogged) View.GONE else View.VISIBLE
        }

        mediator.observe<HomeCommand.Collect>(viewLifecycleOwner) {
            payload["login"] = "login"
        }

        fragment.viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val command = if (userName.isBlank()) LoginContract.CheckAuth()
            else LoginContract.LoggedIn(userName, true)

            mediator.send(command)
        }
    }
}
