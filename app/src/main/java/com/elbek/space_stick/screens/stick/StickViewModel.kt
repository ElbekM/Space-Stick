package com.elbek.space_stick.screens.stick

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.utils.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class StickViewModel(
    private val apiService: StickService,
    application: Application
) : BaseViewModel(application) {

    private var patternPosition = 0

    val wifiName = MutableLiveData<String>()
    val patternsList = MutableLiveData<List<Pattern>>()

    fun init(wifiSsid: String) {
        wifiName.value = wifiSsid

        saveWifiNameToSharedPrefs(wifiSsid)
        fillPatterns()
    }

    fun brightnessSeekBarChanged(position: Int) {
        launch {
            try {
                apiService.setBrightness(position)
            } catch (exception: Exception) {
                processException(exception) {
                    brightnessSeekBarChanged(position)
                }
            }
        }
    }


    fun speedSeekBarChanged(position: Int) {
        launch {
            try {
                apiService.setSpeed(position)
            } catch (exception: Exception) {
                processException(exception) {
                    speedSeekBarChanged(position)
                }
            }
        }
    }

    fun onPreviousButtonClicked() {
        launch {
            try {
                patternPosition -= 1
                apiService.setPattern(patternPosition)
            } catch (exception: Exception) {
                processException(exception) {
                    onPreviousButtonClicked()
                }
            }
        }
    }

    fun onPlayPauseButtonClicked() {

    }

    fun onForwardButtonClicked() {
        launch {
            try {
                patternPosition += 1
                apiService.setPattern(patternPosition)
            } catch (exception: Exception) {
                processException(exception) {
                    onForwardButtonClicked()
                }
            }
        }
    }

    fun onItemClicked(position: Int) {
        launch {
            try {
                patternPosition = position
                apiService.setPattern(position)
            } catch (exception: Exception) {
                processException(exception) {
                    onItemClicked(position)
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
        patternsList.postValue(patterns)
    }

    private fun saveWifiNameToSharedPrefs(wifiName: String) {
        val preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit().putString(Constants.APP_PREFERENCES_WIFI, wifiName).apply()
    }

    class Pattern(
        val name: String
    )
}
