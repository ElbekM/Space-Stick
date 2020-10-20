package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.models.ColorType
import com.elbek.space_stick.models.Rgb
import kotlinx.coroutines.launch

class RgbSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    fun onColorPickerSelected(colorArray: IntArray) {
        val color = Rgb(colorArray[1], colorArray[2], colorArray[3])
        setColor(color)
    }

    fun onChangeColorClicked(type: ColorType) {
        setColor(type.color)
    }

    fun onCustomColorsClicked() {
        //TODO: Open dialog with custom colors
    }

    private fun setColor(color: Rgb) {
        launch {
            try {
                apiService.setColor(
                    r = color.r,
                    g = color.g,
                    b = color.b
                )
            } catch (exception: Exception) {
                processException(exception) {
                    setColor(color)
                }
            }
        }
    }
}
