package com.elbek.space_stick.screens.patternSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.fragment_rgb_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RgbSettingsFragment : BaseDialogFragment<RgbSettingsViewModel>() {

    override val viewModel: RgbSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_rgb_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun bindViewModel() {
        super.bindViewModel()
    }

    private fun initViews() {

        backImageView.setOnClickListener { close() }

        whitePatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        redPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        violetPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        neonPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        bluePatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        liteBluePatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        greenPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        yellowPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        orangePatternColor.setOnClickListener { viewModel.onChangeColorClicked() }
        grayPatternColor.setOnClickListener { viewModel.onChangeColorClicked() }

        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->
            viewModel.onColorPickerSelected(envelope.argb)
        })
    }

    companion object {
        fun newInstance() = RgbSettingsFragment()
    }
}
