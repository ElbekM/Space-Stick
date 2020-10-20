package com.elbek.space_stick.screens.patternSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.core.BaseDialogFragment
import com.elbek.space_stick.models.ColorType
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
    }

    private fun initViews() {

        backImageView.setOnClickListener { close() }

        with(viewModel) {
            whitePatternColor.setOnClickListener { onChangeColorClicked(ColorType.WHITE) }
            redPatternColor.setOnClickListener { onChangeColorClicked(ColorType.RED) }
            violetPatternColor.setOnClickListener { onChangeColorClicked(ColorType.VIOLET) }
            neonPatternColor.setOnClickListener { onChangeColorClicked(ColorType.NEON) }
            bluePatternColor.setOnClickListener { onChangeColorClicked(ColorType.DARK_BLUE) }
            liteBluePatternColor.setOnClickListener { onChangeColorClicked(ColorType.BLUE) }
            greenPatternColor.setOnClickListener { onChangeColorClicked(ColorType.GREEN) }
            yellowPatternColor.setOnClickListener { onChangeColorClicked(ColorType.YELLOW) }
            orangePatternColor.setOnClickListener { onChangeColorClicked(ColorType.ORANGE) }
            additionalColors.setOnClickListener { onCustomColorsClicked() }
        }

        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->
            viewModel.onColorPickerSelected(envelope.argb)
        })
    }

    companion object {
        fun newInstance() = RgbSettingsFragment()
    }
}
