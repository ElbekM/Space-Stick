package com.elbek.space_stick.screens.settings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.SingleLiveEvent

class SettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val wifiName = SingleLiveEvent<String>()
    val patternPosition = SingleLiveEvent<Int>()

    fun init(wifiName: String?, patternPosition: Int) {
        this.wifiName.value = wifiName
        this.patternPosition.value = patternPosition
    }
}
