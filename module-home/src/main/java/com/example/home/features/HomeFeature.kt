package com.example.home.features

import com.example.core.Mediator
import com.example.home.HomeFragment

interface HomeFeature {
    operator fun invoke(fragment: HomeFragment)
}

object HomeCommand {
    class LoggedIn(val userName: String) : Mediator.Command

    class Collect(val payload: HashMap<String, Any> = hashMapOf()) : Mediator.Collect
}