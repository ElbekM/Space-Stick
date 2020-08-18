package com.elbek.space_stick.screens.stick

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel

class StickViewModel(
    private val apiService: StickService,
    application: Application
) : BaseViewModel(application) {

    fun init() {

    }
}
