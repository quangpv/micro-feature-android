package com.example.home.features.login

import com.example.core.BodyPart
import com.example.core.Validate

class LoginBodyPart(
    private val userName: String
) : BodyPart {
    override val validate: Validate =
        if (userName.isBlank()) Validate.fail("User not logged in yet!")
        else Validate.ok()

    override fun build(): Map<String, Any> {
        return mapOf("login" to userName)
    }
}