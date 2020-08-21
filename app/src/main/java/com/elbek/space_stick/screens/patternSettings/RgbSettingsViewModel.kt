package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.LiveEvent
import kotlinx.coroutines.launch

class RgbSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    fun onColorPickerSelected(colorArray: IntArray) {
        val color = Rgb(colorArray[0], colorArray[1], colorArray[2])
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

    data class Rgb(
        val r: Int,
        val g: Int,
        val b: Int
    )

    enum class ColorsType(val color: Rgb) {
        WHITE(Rgb(255, 255, 255)),
        RED(Rgb(255, 0, 48)),
        VIOLET(Rgb(181, 0, 175)),
        NEON(Rgb(123, 0, 183)),
        DARK_BLUE(Rgb(72, 52, 180)),
        BLUE(Rgb(0, 164, 246)),
        GREEN(Rgb(99, 205, 70)),
        YELLOW(Rgb(251, 237, 55)),
        ORANGE(Rgb(255, 142, 0)),
        GRAY(Rgb(81, 124, 136))
    }
}
