package com.elbek.space_stick.screens.stick

import android.app.Application
import android.content.Context
import com.elbek.space_stick.R
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.extensions.modularAdd
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.LiveEvent
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
import com.elbek.space_stick.common.utils.Constants
import com.elbek.space_stick.models.Pattern
import com.elbek.space_stick.models.PatternType
import com.elbek.space_stick.models.WifiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class StickViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    private var wifiName: String? = null
    private var patternPosition = PatternType.DEFAULT.position
    private var patternsCount = 0

    val onPause = SingleLiveEvent<Boolean>()
    val wifiStatus = SingleLiveEvent<WifiStatus>()
    val patternsList = SingleLiveEvent<List<Pattern>>()

    val launchSettingsScreen = SingleLiveEvent<Pair<String?, Int>>()
    val launchRgbSettingsScreen = LiveEvent()
    val launchPatternSettingsScreen = LiveEvent()

    fun init(wifiSsid: String) {
        wifiName = wifiSsid
        wifiStatus.value = WifiStatus.CONNECTED
        onPause.value = false

        saveWifiNameToSharedPrefs(wifiSsid)
        setDefaultParameters()
        fillPatterns()
    }

    fun onSettingsClicked() {
        launchSettingsScreen.value = Pair(wifiName, patternPosition + 1)
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
        setPattern(if (onPause.value!!) patternPosition else PatternType.CONSTANT.position)
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
        if (position == PatternType.CONSTANT.position) {
            setPattern(position)
            launchRgbSettingsScreen.call()
        } else {
            launchPatternSettingsScreen.call()
        }
    }

    private fun setDefaultParameters() {
        launch(Dispatchers.Main) {
            setSpeed(100)
            setPattern(PatternType.START.position)
            delay(3000)
            setPattern(PatternType.DEFAULT.position)
            setBrightness(100)
        }
    }

    private fun setBrightness(position: Int) {
        launch {
            try {
                apiService.setBrightness(position)
                wifiStatus.postValue(WifiStatus.CONNECTED)
            } catch (exception: Exception) {
                wifiStatus.postValue(WifiStatus.FAILED)
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
                wifiStatus.postValue(WifiStatus.CONNECTED)
            } catch (exception: Exception) {
                wifiStatus.postValue(WifiStatus.FAILED)
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
                wifiStatus.postValue(WifiStatus.CONNECTED)
            } catch (exception: Exception) {
                wifiStatus.postValue(WifiStatus.FAILED)
                processException(exception) {
                    setPattern(patternPosition)
                }
            }
        }
    }
    //TODO: Fix positions
    private fun fillPatterns() {
        val patterns = mutableListOf<Pattern>()
        patterns.apply {
            add(Pattern("static", 16, R.drawable.ic_luke_skywalker_lightsaber))
            add(Pattern("juggle", 0, R.drawable.ic_pattern1))
            add(Pattern("sinelon", 1, R.drawable.ic_pattern2))
            add(Pattern("confetti", 2, R.drawable.ic_pattern3))
            add(Pattern("rainbowWithGlitter", 3, R.drawable.ic_pattern4))
            add(Pattern("rainbow1", 4, R.drawable.ic_pattern5))
            add(Pattern("rainbow2", 5, R.drawable.ic_pattern6))
            add(Pattern("rainbow3", 6, R.drawable.ic_pattern7))
            add(Pattern("rainbow4", 7, R.drawable.ic_pattern8))
            add(Pattern("rainbow5", 8, R.drawable.ic_pattern9))
            add(Pattern("rainbow6", 9, R.drawable.ic_pattern10))
            add(Pattern("three_sin1", 10, R.drawable.ic_pattern12))
            add(Pattern("three_sin2", 11, R.drawable.ic_pattern13))
            add(Pattern("three_sin3", 12, R.drawable.ic_pattern14))
            add(Pattern("three_sin4", 13, R.drawable.ic_pattern15))
            add(Pattern("blendwave", 14, R.drawable.ic_pattern14))
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
