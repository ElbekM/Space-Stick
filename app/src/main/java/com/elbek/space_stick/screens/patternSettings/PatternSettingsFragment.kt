package com.elbek.space_stick.screens.patternSettings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_pattern_settings.*
import kotlinx.android.synthetic.main.fragment_pattern_settings.backImageView
import kotlinx.android.synthetic.main.fragment_rgb_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatternSettingsFragment : BaseDialogFragment<PatternSettingsViewModel>() {

    override val viewModel: PatternSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pattern_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun bindViewModel() {
        super.bindViewModel()
    }
    //TODO: refactoring
    private fun initViews() {

        backImageView.setOnClickListener { close() }

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
        graphView.addSeries(sin)
        graphView.addSeries(cos)
    }

    companion object {
        fun newInstance() = PatternSettingsFragment()
    }
}
