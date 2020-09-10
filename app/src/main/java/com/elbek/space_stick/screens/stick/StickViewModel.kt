package com.elbek.space_stick.screens.stick

import android.app.Application
import android.content.Context
import com.elbek.space_stick.R
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.extensions.modularAdd
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.LiveEvent
import com.elbek.space_stick.common.mvvm.commands.SingleLiveEvent
import com.elbek.space_stick.common.utils.Constants
import com.elbek.space_stick.models.Pattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class StickViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    private var patternPosition = 0
    private var patternsCount = 0

    val onPause = SingleLiveEvent<Boolean>()
    val wifiName = SingleLiveEvent<String>()
    val patternsList = SingleLiveEvent<List<Pattern>>()

    val launchSettingsScreen = LiveEvent()
    val launchRgbSettingsScreen = LiveEvent()
    val launchPatternSettingsScreen = LiveEvent()

    fun init(wifiSsid: String) {
        this.wifiName.value = wifiSsid
        onPause.value = false

        saveWifiNameToSharedPrefs(wifiSsid)
        setDefaultParameters()
        fillPatterns()
    }

    fun onSettingsClicked() {
        launchSettingsScreen.call()
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
        setPattern(if (onPause.value!!) patternPosition else 0)
        onPause.value = !onPause.value!!
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
        if (position == 0) {
            onItemClicked(16)
            launchRgbSettingsScreen.call()
        } else {
            launchPatternSettingsScreen.call()
        }
    }

    private fun setDefaultParameters() {
        patternPosition = 0
        launch(Dispatchers.Main) {
            setSpeed(100)
            setPattern(2)
            delay(3000)
            setPattern(patternPosition)
            setBrightness(100)
            setSpeed(10)
        }

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
            add(Pattern("juggle", R.drawable.ic_pattern1))
            add(Pattern("sinelon", R.drawable.ic_pattern2))
            add(Pattern("confetti", R.drawable.ic_pattern3))
            add(Pattern("rainbowWithGlitter", R.drawable.ic_pattern4))
            add(Pattern("rainbow1", R.drawable.ic_pattern5))
            add(Pattern("rainbow2", R.drawable.ic_pattern6))
            add(Pattern("rainbow3", R.drawable.ic_pattern7))
            add(Pattern("rainbow4", R.drawable.ic_pattern8))
            add(Pattern("rainbow5", R.drawable.ic_pattern9))
            add(Pattern("rainbow6", R.drawable.ic_pattern10))
            add(Pattern("three_sin1", R.drawable.ic_pattern12))
            add(Pattern("three_sin2", R.drawable.ic_pattern13))
            add(Pattern("three_sin3", R.drawable.ic_pattern14))
            add(Pattern("three_sin4", R.drawable.ic_pattern15))
            add(Pattern("blendwave", R.drawable.ic_pattern14))
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
}
