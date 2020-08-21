package com.elbek.space_stick.screens.stick

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.extensions.modularAdd
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class StickViewModel(private val apiService: StickService, application: Application)
    : BaseViewModel(application) {

    private var patternPosition = 0
    private var patternsCount = 0
    //TODO: onPause observe and change icon
    val isOnPause = MutableLiveData<Boolean>(false)
    val wifiName = MutableLiveData<String>()
    val patternsList = MutableLiveData<List<Pattern>>()

    fun init(wifiSsid: String?) {
        val wifiName = wifiSsid ?: "SpaceStickWiFi"
        this.wifiName.value = wifiName

        saveWifiNameToSharedPrefs(wifiName)
        setDefaultParameters()
        fillPatterns()
    }

    fun brightnessSeekBarChanged(position: Int) {
        setBrightness(position)
    }

    fun speedSeekBarChanged(position: Int) {
        setSpeed(position)
    }

    fun onPreviousButtonClicked() {
        patternPosition = (patternPosition - 1).modularAdd(patternsCount)
        setPattern(patternPosition)
    }

    fun onPlayPauseButtonClicked() {
        setPattern(if (isOnPause.value!!) patternPosition else 0)
        isOnPause.value = !isOnPause.value!!
    }

    fun onForwardButtonClicked() {
        patternPosition = (patternPosition + 1).modularAdd(patternsCount)
        setPattern(patternPosition)
    }

    fun onItemClicked(position: Int) {
        patternPosition = position
        setPattern(patternPosition)
    }

    fun onItemLongClicked(position: Int) {

    }

    private fun setDefaultParameters() {
        patternPosition = 0
        setPattern(patternPosition)
        setBrightness(100)
        setSpeed(10)
    }

    private fun setBrightness(position: Int) {
        launch {
            try {
                apiService.setBrightness(position)
            } catch (exception: Exception) {
                processException(exception) {
                    setBrightness(position)
                }
            }
        }
    }

    private fun setSpeed(position: Int) {
        launch {
            try {
                apiService.setSpeed(position)
            } catch (exception: Exception) {
                processException(exception) {
                    setSpeed(position)
                }
            }
        }
    }

    private fun setPattern(patternPosition: Int) {
        launch {
            try {
                apiService.setPattern(patternPosition)
            } catch (exception: Exception) {
                processException(exception) {
                    setPattern(patternPosition)
                }
            }
        }
    }

    private fun fillPatterns() {
        val patterns = mutableListOf<Pattern>()
        patterns.apply {
            add(Pattern("juggle"))
            add(Pattern("sinelon"))
            add(Pattern("confetti"))
            add(Pattern("rainbowWithGlitter"))
            add(Pattern("rainbow1"))
            add(Pattern("rainbow2"))
            add(Pattern("rainbow3"))
            add(Pattern("rainbow4"))
            add(Pattern("rainbow5"))
            add(Pattern("rainbow6"))
            add(Pattern("three_sin1"))
            add(Pattern("three_sin2"))
            add(Pattern("three_sin3"))
            add(Pattern("three_sin4"))
            add(Pattern("blendwave"))
        }
        patternsCount = patterns.size
        patternsList.postValue(patterns)
    }

    private fun saveWifiNameToSharedPrefs(wifiName: String) {
        if (wifiName != Constants.DEFAULT_WIFI_NAME) {
            val preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
            preferences.edit().putString(Constants.APP_PREFERENCES_WIFI, wifiName).apply()
        }
    }

    class Pattern(
        val name: String
    )
}
