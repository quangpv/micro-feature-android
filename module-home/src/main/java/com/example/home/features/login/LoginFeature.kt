package com.example.home.features.login

import android.view.View
import com.example.core.block
import com.example.core.getViewModel
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
        val viewModel = getViewModel<LoginViewModel>()

        btnLogin = viewBy<View>(R.id.btnGotoLogin).apply { visibility = View.VISIBLE }

        val userName = arguments?.getString(HomeFragment.LOGGED) ?: ""

        btnLogin.setOnClickListener {
            emitter.emit(GotoLoginAction(this))
        }

        viewModel.loggedIn.observe(viewLifecycleOwner) {
            btnLogin.visibility = View.GONE
            mediator.send(HomeCommand.LoggedIn(it))
        }

        mediator.observe<HomeCommand.Collect>(viewLifecycleOwner) {
            payload["login"] = "login"
        }

        viewModel.checkAuth(userName)
    }
}
