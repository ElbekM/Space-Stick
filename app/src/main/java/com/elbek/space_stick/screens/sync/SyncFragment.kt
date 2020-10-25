package com.elbek.space_stick.screens.sync

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elbek.space_stick.R
import com.elbek.space_stick.common.core.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SyncFragment : BaseDialogFragment<SyncViewModel>() {

    override val viewModel: SyncViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sync, container, false)

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

    }

    companion object {
        fun newInstance() = SyncFragment()
    }
}
