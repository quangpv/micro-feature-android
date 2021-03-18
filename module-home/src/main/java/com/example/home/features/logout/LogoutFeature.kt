package com.example.home.features.logout

import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.core.MediatorViewModel
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.view
import com.example.home.HomeFragment
import com.example.home.HomeMediator
import com.example.home.R
import com.example.home.features.HomeFeature
import com.example.modules.authenticate.AuthenticateProxy
import kotlinx.coroutines.launch

class LogoutFeature : HomeFeature {
    private lateinit var btnLogout: View

    override fun invoke(fragment: HomeFragment) = block(fragment) {
        val viewModel = getViewModel<LogoutViewModel>(mediator)
        btnLogout = view(R.id.btnLogout).apply { visibility = View.GONE }

        btnLogout.setOnClickListener {
            viewModel.logout()
        }

        mediator.loggedOut.observe(viewLifecycleOwner) {
            btnLogout.visibility = View.GONE
        }

        mediator.loggedIn.observe(viewLifecycleOwner) {
            btnLogout.visibility = View.VISIBLE
        }
    }
}

class LogoutViewModel(private val proxy: AuthenticateProxy) : MediatorViewModel<HomeMediator>() {

    fun logout() {
        viewModelScope.launch {
            proxy.logout()
            mediator.loggedOut.call()
        }
    }
}