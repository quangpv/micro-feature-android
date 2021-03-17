package com.example.modules.authenticate

interface UserModel {
    val userName: String

    object Empty : UserModel {
        override val userName: String
            get() = ""
    }
}

