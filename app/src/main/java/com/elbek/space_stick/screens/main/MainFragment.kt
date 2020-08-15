package com.elbek.space_stick.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseDialogFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun bindViewModel() {

    }

    private fun initViews() {
        Toast.makeText(context, "YEEEE", Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
