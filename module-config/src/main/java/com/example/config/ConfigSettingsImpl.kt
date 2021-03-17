package com.example.config

import com.example.modules.configure.ConfigSettings

class ConfigSettingsImpl : ConfigSettings {
    override val isEnableLogout: Boolean = true
    override val isEnableLoadConfig: Boolean = true
    override val isEnableLogin: Boolean = true
}