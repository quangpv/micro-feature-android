package com.example.home.features.login

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.core.BodyPart
import com.example.core.MediatorViewModel
import com.example.core.block
import com.example.core.getViewModel
import com.example.core.launch
import com.example.core.lookup
import com.example.core.viewBy
import com.example.home.HomeEmitter
import com.example.home.HomeFragment
import com.example.home.HomeMediator
import com.example.home.R
import com.example.home.actions.GotoLoginAction
import com.example.home.features.HomeFeature
import com.example.modules.module.ModuleProxy

class GotoLoginFeature : HomeFeature {
    private lateinit var btnLogin: TextView

    private val emitter: HomeEmitter by lookup()

    @SuppressLint("SetTextI18n")
    override fun invoke(fragment: Fragment, mediator: HomeMediator) = block(fragment) {
        val viewModel = getViewModel<GotoLoginViewModel>(mediator)
        val userName = arguments?.getString(HomeFragment.LOGGED) ?: ""

        btnLogin = viewBy<TextView>(R.id.btnGotoLogin).apply { visibility = View.VISIBLE }

        btnLogin.setOnClickListener {
            emitter.emit(GotoLoginAction(this))
        }

        viewModel.validateFail.observe(viewLifecycleOwner) {
            btnLogin.text = "Go to Login ($it)"
        }

        mediator.loggedIn.observe(viewLifecycleOwner) {
            btnLogin.visibility = View.GONE
        }

        mediator.loggedOut.observe(viewLifecycleOwner) {
            btnLogin.visibility = View.VISIBLE
            viewModel.clean()
        }

        mediator.collectForm.observe(viewLifecycleOwner) {
            it += viewModel.createPart()
        }

        viewModel.checkAuth(userName)
    }
}

class GotoLoginViewModel(private val proxy: ModuleProxy) : MediatorViewModel<HomeMediator>() {
    private var mUserName = ""

    val validateFail = MutableLiveData<String>()

    fun checkAuth(userName: String) = launch {
        val name = if (userName.isNotBlank()) userName
        else proxy.authenticate.getUserLoggedIn().userName

        if (!name.isBlank()) {
            mUserName = name
            mediator.loggedIn.value = name
        }
    }

    fun createPart(): BodyPart {
        val part = LoginBodyPart(mUserName)
        val validate = part.validate
        if (!validate.isValid) validateFail.value = validate.message
        return part
    }

    fun clean() {
        mUserName = ""
    }
}
