package com.example.home.features.logout

import android.view.View
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.lookup
import com.example.core.observe
import com.example.core.view
import com.example.home.HomeEmitter
import com.example.home.HomeFragment
import com.example.home.R
import com.example.home.actions.GotoLoginAction
import com.example.home.features.HomeCommand
import com.example.home.features.HomeFeature

class LogoutFeature : HomeFeature {
    private lateinit var btnLogout: View

    private val emitter: HomeEmitter by lookup()

    override fun invoke(fragment: HomeFragment) = block(fragment) {
        val viewModel = getViewModel<LogoutViewModel>()
        btnLogout = view(R.id.btnLogout).apply { visibility = View.GONE }

        btnLogout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.logout.observe(viewLifecycleOwner) {
            emitter.emit(GotoLoginAction(this@block))
        }

        mediator.observe<HomeCommand.LoggedIn>(viewLifecycleOwner) {
            btnLogout.visibility = View.VISIBLE
        }
    }
}