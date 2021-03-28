package com.example.home.features.logout

import android.view.View
import androidx.fragment.app.Fragment
import com.example.core.MediatorViewModel
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.launch
import com.example.core.view
import com.example.home.HomeMediator
import com.example.home.R
import com.example.home.features.HomeFeature
import com.example.modules.module.ModuleProxy

class LogoutFeature : HomeFeature {
    private lateinit var btnLogout: View

    override fun invoke(fragment: Fragment, mediator: HomeMediator) = block(fragment) {
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

class LogoutViewModel(private val proxy: ModuleProxy) : MediatorViewModel<HomeMediator>() {

    fun logout() {
        launch {
            proxy.authenticate.logout()
            mediator.loggedOut.call()
        }
    }
}