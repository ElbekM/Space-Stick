package com.elbek.space_stick.screens.patternSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_pattern_settings.*
import kotlinx.android.synthetic.main.fragment_pattern_settings.backImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatternSettingsFragment : BaseDialogFragment<PatternSettingsViewModel>() {

    override val viewModel: PatternSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        viewModel.updateSinChartData.observe { sin ->
            graphView.addSeries(sin)
        }

        viewModel.updateCosChartData.observe { cos ->
            graphView.addSeries(cos)
        }
    }

    private fun initViews() {
        backImageView.setOnClickListener { close() }
    }

    companion object {
        fun newInstance() = PatternSettingsFragment()
    }
}
