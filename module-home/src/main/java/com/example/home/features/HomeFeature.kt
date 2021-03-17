package com.example.home.features

import com.example.core.Mediator
import com.example.home.HomeFragment

interface HomeFeature {
    operator fun invoke(fragment: HomeFragment)
}

object HomeCommand {
    interface LoggedIn : Mediator.Command {
        val isLogged: Boolean
        val userName: String
    }

    class Collect(val payload: HashMap<String, Any> = hashMapOf()) : Mediator.Collect
}