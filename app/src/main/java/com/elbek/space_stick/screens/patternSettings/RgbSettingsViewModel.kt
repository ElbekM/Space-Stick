package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import android.content.Context
import com.elbek.space_stick.R
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.LiveEvent
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
import com.elbek.space_stick.database.ColorDatabaseProvider
import com.elbek.space_stick.models.ColorType
import com.elbek.space_stick.models.Rgb
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RgbSettingsViewModel(
    private val apiService: StickService,
    private val colorDatabaseProvider: ColorDatabaseProvider,
    application: Application
) : BaseViewModel(application) {

    private var customColorList = mutableListOf<Rgb>()

    val showColorPickerDialogLiveEvent = LiveEvent()
    val addColorTextVisible = SingleLiveEvent<Boolean>()
    val customColorsLayoutVisible = SingleLiveEvent<Boolean>()
    val customColors = SingleLiveEvent<MutableList<Rgb>>()

    override fun back() {
        customColorsLayoutVisible.value.let { visible ->
            if (visible != null && visible)
                customColorsLayoutVisible.value = false
            else
                super.back()
        }
    }

    fun init() = launch {
        colorDatabaseProvider.getCustomColors().let { colors ->
            customColorList = colors ?: mutableListOf()
            customColors.postValue(customColorList)
            withContext(Dispatchers.Main) {
                addColorTextVisible.value = colors.isNullOrEmpty()
            }
        }
    }

    fun onColorPickerSelected(colorArray: IntArray) {
        val color = Rgb(colorArray[1], colorArray[2], colorArray[3])
        setColor(color)
    }

    fun onChangeColorClicked(type: ColorType) {
        setColor(type.color)
    }

    fun onChangeColorClicked(position: Int) {
        setColor(customColorList[position])
    }

    fun onCustomColorsClicked() {
        customColorsLayoutVisible.value = true
    }

    fun onAddNewColorButtonClicked() {
        showColorPickerDialogLiveEvent.call()
    }

    fun showColorPickerDialog(context: Context) {
        ColorPickerDialog.Builder(context)
            .setTitle(R.string.scr_rgb_settings_lbl_color_picker)
            .setPositiveButton(R.string.scr_any_msg_save, ColorEnvelopeListener { envelope, _ ->
                addColorToDatabase(envelope.argb)
            })
            .setNegativeButton(R.string.scr_any_msg_cancel) { dialogInterface, _ ->
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

    private fun addColorToDatabase(colorArray: IntArray) {
        addColorTextVisible.value = false

        customColorList.add(Rgb(colorArray[1], colorArray[2], colorArray[3]))
        customColorList.let {
            customColors.value = it
            launch { colorDatabaseProvider.addColorToDatabase(it[it.lastIndex]) }
        }
    }
}
