package com.elbek.space_stick.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.elbek.space_stick.common.mvvm.showAllowingStateLoss
import com.elbek.space_stick.screens.stick.StickFragment
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseDialogFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init(requireActivity())
    }

    override fun bindViewModel() {
        viewModel.launchStickScreenCommand.observe {
            StickFragment
                .newInstance()
                .showAllowingStateLoss(childFragmentManager)
        }

        viewModel.wifiSsid.observe {
            wifiNameTextView.text = it ?: "unknown ssid"
        }
    }

    private fun initViews() {
        checkConnectionButton.setOnClickListener {
            viewModel.onCheckConnectionClicked()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
