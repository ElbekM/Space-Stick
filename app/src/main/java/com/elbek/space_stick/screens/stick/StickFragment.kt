package com.elbek.space_stick.screens.stick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.elbek.space_stick.R
import com.elbek.space_stick.common.mvvm.BaseDialogFragment
import com.elbek.space_stick.common.mvvm.showAllowingStateLoss
import com.elbek.space_stick.screens.patternSettings.RgbSettingsFragment
import com.elbek.space_stick.screens.stick.adapter.PatternAdapter
import kotlinx.android.synthetic.main.fragment_stick.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StickFragment : BaseDialogFragment<StickViewModel>(), SeekBar.OnSeekBarChangeListener {

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
            requireArguments().getString(wifiNameKey, "")
        )
    }
    
    override fun bindViewModel() {
        super.bindViewModel()

        viewModel.wifiName.observe {
            it?.let {
                wifiNameTextView.text = it
            }
        }

        viewModel.onPause.observe {
            it?.let { pause ->
                playPauseButtonImageView.apply {
                    if (pause)
                        setImageResource(R.drawable.play)
                    else
                        setImageResource(R.drawable.pause)
                }
            }
        }

        viewModel.patternsList.observe {
            it.let { patterns ->
                val adapter = PatternAdapter()
                stickPatternsGridView.adapter = adapter
                adapter.items = patterns
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.launchRgbSettingsScreen.observe {
            RgbSettingsFragment
                .newInstance()
                .showAllowingStateLoss(childFragmentManager)
        }
    }

    private fun initViews() {
        brightnessSeekBar.setOnSeekBarChangeListener(this)
        speedSeekBar.setOnSeekBarChangeListener(this)

        previousButtonImageView.setOnClickListener { viewModel.onPreviousButtonClicked() }
        playPauseButtonImageView.setOnClickListener { viewModel.onPlayPauseButtonClicked() }
        forwardButtonImageView.setOnClickListener { viewModel.onForwardButtonClicked() }

        stickPatternsGridView.setOnItemClickListener { _, _, position, _ ->
            viewModel.onItemClicked(position)
        }
        stickPatternsGridView.setOnItemLongClickListener { _, _, position, _ ->
            viewModel.onItemLongClicked(position)
            return@setOnItemLongClickListener true
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { }
    override fun onStartTrackingTouch(p0: SeekBar?) { }
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        when (seekBar.id) {
            R.id.brightnessSeekBar -> viewModel.brightnessSeekBarChanged(seekBar.progress)
            R.id.speedSeekBar -> viewModel.speedSeekBarChanged(seekBar.progress)
        }
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
