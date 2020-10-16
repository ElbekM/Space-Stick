package com.elbek.space_stick.screens.patternSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.elbek.space_stick.models.ColorType
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.fragment_rgb_settings.*
import kotlinx.android.synthetic.main.view_color_list.*
import kotlinx.android.synthetic.main.view_custom_colors.*
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

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.customColorsLayoutVisible.observe {
            customColorsLayout.isVisible = it ?: false
        }

        viewModel.showColorPickerDialogLiveEvent.observe {
            viewModel.showColorPickerDialog(requireContext())
        }
    }

    private fun initViews() {
        backImageView.setOnClickListener { close() }
        addNewColorButton.setOnClickListener { viewModel.onAddNewColorButtonClicked() }
        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->
            viewModel.onColorPickerSelected(envelope.argb)
        })

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
    }

    companion object {
        fun newInstance() = RgbSettingsFragment()
    }
}
