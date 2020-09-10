package com.elbek.space_stick.screens.settings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel

class SettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    fun init() {

    }
}
