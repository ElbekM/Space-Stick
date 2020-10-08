package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.models.ColorsType
import com.elbek.space_stick.models.Rgb
import kotlinx.coroutines.launch

class RgbSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    fun onColorPickerSelected(colorArray: IntArray) {
        val color = Rgb(colorArray[1], colorArray[2], colorArray[3])
        setColor(color)
    }

    fun onChangeColorClicked(type: ColorsType) {
        setColor(type.color)
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
