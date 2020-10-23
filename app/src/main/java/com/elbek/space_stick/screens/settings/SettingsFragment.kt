package com.elbek.space_stick.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.elbek.space_stick.R
import com.elbek.space_stick.common.core.BaseDialogFragment
import com.elbek.space_stick.screens.stick.StickFragment.Companion.wifiNameKey
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.backImageView
import kotlinx.android.synthetic.main.fragment_settings.wifiNameTextView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseDialogFragment<SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
        viewModel.init(
            requireArguments().getString(wifiNameKey),
            requireArguments().getInt(patternPositionKey)
        )
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.appVersion.observe {
            it?.let { appVersionTextView.text = it }
        }

        viewModel.wifiName.observe {
            it?.let { wifiNameTextView.text = it }
        }

        viewModel.patternPosition.observe {
            it?.let { patternNumberTextView.text = it.toString() }
        }
    }

    private fun initViews() {
        backImageView.setOnClickListener { close() }
    }

    companion object {
        val patternPositionKey: String = ::patternPositionKey.name

        fun newInstance(wifiName: String, patternPosition: Int) = SettingsFragment().apply {
            arguments = bundleOf(
                wifiNameKey to wifiName,
                patternPositionKey to patternPosition
            )
        }
    }
}
