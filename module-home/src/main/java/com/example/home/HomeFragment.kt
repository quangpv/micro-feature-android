package com.example.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.CommandLiveData
import com.example.core.Form
import com.example.core.Mediator
import com.example.core.StatusWindowOwner
import com.example.core.lookup
import com.example.home.features.HomeFeature

class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        const val LOGGED = "logged"
    }

    private val features: List<HomeFeature> by lookup()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mediator = HomeMediator()
        features.forEach { it(this, mediator) }
    }
}

class HomeMediator : Mediator, StatusWindowOwner {
    override val error: LiveData<out Throwable> = MutableLiveData()
    override val loading: LiveData<Boolean> = MutableLiveData()

    val loggedOut = CommandLiveData<Any>()
    val loggedIn = CommandLiveData<String>()
    val collectForm = CommandLiveData<Form>()
}