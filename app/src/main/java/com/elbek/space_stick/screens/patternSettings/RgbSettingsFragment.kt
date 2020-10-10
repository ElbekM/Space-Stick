package com.elbek.space_stick.screens.patternSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.elbek.space_stick.models.ColorsType
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
            whitePatternColor.setOnClickListener { onChangeColorClicked(ColorsType.WHITE) }
            redPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.RED) }
            violetPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.VIOLET) }
            neonPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.NEON) }
            bluePatternColor.setOnClickListener { onChangeColorClicked(ColorsType.DARK_BLUE) }
            liteBluePatternColor.setOnClickListener { onChangeColorClicked(ColorsType.BLUE) }
            greenPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.GREEN) }
            yellowPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.YELLOW) }
            orangePatternColor.setOnClickListener { onChangeColorClicked(ColorsType.ORANGE) }
            grayPatternColor.setOnClickListener { onChangeColorClicked(ColorsType.GRAY) }
        }

        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->
            viewModel.onColorPickerSelected(envelope.argb)
        })
    }

    companion object {
        fun newInstance() = RgbSettingsFragment()
    }
}
