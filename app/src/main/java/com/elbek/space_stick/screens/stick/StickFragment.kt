package com.elbek.space_stick.screens.stick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StickFragment : BaseDialogFragment<StickViewModel>() {

    override val viewModel: StickViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_stick, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init(
            requireArguments().getString(wifiNameKey)
        )
    }

    override fun bindViewModel() {

    }

    private fun initViews() {

    }

    companion object {
        val wifiNameKey: String = ::wifiNameKey.name

        fun newInstance(wifiSsid: String) =
            StickFragment().apply {
                arguments = Bundle().apply {
                    putString(wifiNameKey, wifiSsid)
                }
            }
    }
}
