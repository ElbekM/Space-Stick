package com.elbek.space_stick.screens.stick

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel

class StickViewModel(
    private val apiService: StickService,
    application: Application
) : BaseViewModel(application) {

    val wifiName = MutableLiveData<String>()
    val patternsList = MutableLiveData<List<Pattern>>()

    fun init(wifiSsid: String) {
        wifiName.value = wifiSsid

        fillPatterns()
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

    class Pattern(
        val name: String
    )
}
