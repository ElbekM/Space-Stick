package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import kotlinx.coroutines.launch

class RgbSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    fun init() {

    }

    fun onColorPickerSelected(rgb: IntArray) {

    }

    fun onChangeColorClicked() {

    }

    private fun setColor(rgb: List<Int>) {
        launch {
            try {
                apiService.setColor(
                    r = rgb[0],
                    g = rgb[1],
                    b = rgb[2]
                )
            } catch (exception: Exception) {
                processException(exception) {
                    setColor(rgb)
                }
            }
        }
    }
}
