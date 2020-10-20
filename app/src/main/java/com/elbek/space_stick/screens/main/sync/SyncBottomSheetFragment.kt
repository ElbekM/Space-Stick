package com.elbek.space_stick.screens.main.sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.elbek.space_stick.R
import com.elbek.space_stick.common.core.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_sync_ap_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SyncBottomSheetFragment : BaseBottomSheetDialogFragment<SyncBottomSheetViewModel>() {

    override val viewModel: SyncBottomSheetViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sync_ap_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
        viewModel.init(
            requireArguments().getString(wifiSsidKey),
            requireArguments().getString(wifiPasswordKey)
        )
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.wifiSsidField.observe { ssid ->
            apSsidEditText.setText(ssid)
        }

        viewModel.wifiPasswordField.observe { password ->
            apPasswordEditText.setText(password)
        }
    }

    private fun initViews() {
        sendWifiDataButton.setOnClickListener {
            viewModel.onSendWifiDataClicked(
                apSsidEditText.text.toString(),
                apPasswordEditText.text.toString()
            )
        }
    }

    companion object {
        private val wifiSsidKey: String = ::wifiSsidKey.name
        private val wifiPasswordKey: String = ::wifiPasswordKey.name

        fun newInstance(wifiSsid: String?, wifiPassword: String?) =
            SyncBottomSheetFragment().apply {
                arguments = bundleOf(
                    wifiSsidKey to wifiSsid,
                    wifiPasswordKey to wifiPassword
                )
            }
    }
}
