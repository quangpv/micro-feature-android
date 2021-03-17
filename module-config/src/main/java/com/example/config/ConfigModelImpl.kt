package com.example.config

import com.example.modules.configure.ConfigModel

class ConfigModelImpl(override val value: String) : ConfigModel {
    override fun toString(): String {
        return value
    }
}