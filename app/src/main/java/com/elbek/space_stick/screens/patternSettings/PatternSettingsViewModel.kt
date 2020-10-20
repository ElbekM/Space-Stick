package com.elbek.space_stick.screens.patternSettings

import android.app.Application
import android.graphics.Color
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class PatternSettingsViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val updateSinChartData = SingleLiveEvent<LineGraphSeries<DataPoint>>()
    val updateCosChartData = SingleLiveEvent<LineGraphSeries<DataPoint>>()

    fun init() {
        //TODO: refactoring
        val sin = LineGraphSeries<DataPoint>()
        val cos = LineGraphSeries<DataPoint>()

        var x: Double = 0.0
        var y: Double

        val numDataPoints = 500
        for (num in 0..numDataPoints) {
            x += 0.1
            y = kotlin.math.sin(x)
            val y2 = kotlin.math.cos(x)
            sin.appendData(DataPoint(x, y), true, 60)
            cos.appendData(DataPoint(x, y2), true, 60)
        }

        sin.color = Color.RED
        cos.color = Color.BLUE

        updateSinChartData.value = sin
        updateCosChartData.value = cos
    }
}