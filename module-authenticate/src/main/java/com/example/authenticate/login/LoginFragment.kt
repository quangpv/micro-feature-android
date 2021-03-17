package com.example.authenticate.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.authenticate.AuthenticateEmitter
import com.example.authenticate.R
import com.example.core.lookup
import com.example.core.view
import com.example.core.viewBy
import com.example.core.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val emitter by lookup<AuthenticateEmitter>()

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view(R.id.btnLogin).setOnClickListener {
            viewModel.login(userName)
        }

        viewModel.loginSuccess.observe(this) {
            emitter.emit(LoginSuccessAction(this, userName))
        }
    }

    private val userName get() = viewBy<TextView>(R.id.edtUserName).text.toString()
}