package com.elbek.space_stick.screens.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.core.BaseDialogFragment
import com.elbek.space_stick.common.extensions.toRvalue
import com.elbek.space_stick.screens.main.sync.SyncBottomSheetFragment
import com.elbek.space_stick.screens.stick.StickFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseDialogFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.launchStickScreenCommand.observe {
            StickFragment
                .newInstance(it!!)
                .showAllowingStateLoss(childFragmentManager)
        }

        viewModel.launchSystemSettingsCommand.observe {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", requireActivity().packageName, null)
                startActivity(this)
            }
        }

        viewModel.launchNetworkSettingsCommand.observe {
            Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                startActivity(this)
            }
        }

        viewModel.launchSyncBottomSheetCommand.observe {
            it?.let { (ssid, password) ->
                SyncBottomSheetFragment
                    .newInstance(ssid, password)
                    .show()
            }
        }

        viewModel.wifiSsid.observe {
            wifiNameTextView.text = it
        }

        viewModel.connectionState.observe {
            it?.let { connection ->
                if (connection) {
                    checkConnectionButton.text = R.string.scr_main_lbl_connecting.toRvalue()
                } else {
                    checkConnectionButton.text = R.string.scr_main_lbl_connect.toRvalue()
                }
            }
        }
    }

    private fun initViews() {
        checkConnectionButton.setOnClickListener { viewModel.onCheckConnectionClicked() }
        syncButton.setOnClickListener { viewModel.onSyncClicked() }
        syncModeButton.setOnClickListener { viewModel.onSyncModeClicked() }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
