package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import android.content.Context
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.LiveEvent
import com.elbek.space_stick.common.mvvm.commands.SingleLiveEvent
import com.elbek.space_stick.models.ColorType
import com.elbek.space_stick.models.Rgb
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.coroutines.launch

class RgbSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val showColorPickerDialogLiveEvent = LiveEvent()
    val customColorsLayoutVisible = SingleLiveEvent<Boolean>()

    override fun back() {
        customColorsLayoutVisible.value.let { visible ->
            if (visible != null && visible)
                customColorsLayoutVisible.value = false
            else
                super.back()
        }
    }

    fun onColorPickerSelected(colorArray: IntArray) {
        val color = Rgb(colorArray[1], colorArray[2], colorArray[3])
        setColor(color)
    }

    fun onChangeColorClicked(type: ColorType) {
        setColor(type.color)
    }

    fun onCustomColorsClicked() {
        customColorsLayoutVisible.value = true
    }

    fun onAddNewColorButtonClicked() {
        showColorPickerDialogLiveEvent.call()
    }

    fun showColorPickerDialog(context: Context) {
        ColorPickerDialog.Builder(context)
            .setTitle("ColorPicker Dialog")
            .setPositiveButton("Save", ColorEnvelopeListener { envelope, _ ->
                //TODO: Save color into database
                envelope.argb
            })
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .attachAlphaSlideBar(false)
            .attachBrightnessSlideBar(false)
            .setBottomSpace(12)
            .show()
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
