package com.elbek.space_stick.common.mvvm

import com.elbek.space_stick.R

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


abstract class BaseDialogFragment<TViewModel> : DialogFragment() where TViewModel : BaseViewModel {
    private val originalScreenOrientationKey: String = ::originalScreenOrientationKey.name

    protected open var customTheme: Int = R.style.AppTheme

    protected open val screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    protected abstract val viewModel: TViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, customTheme)
        isCancelable = false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.destroy()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (arguments == null) {
            arguments = Bundle()
        }

        arguments!!.putInt(originalScreenOrientationKey, activity!!.requestedOrientation)
    }

    override fun onResume() {
        super.onResume()

        activity!!.requestedOrientation = screenOrientation

        viewModel.start()
    }

    override fun onPause() {
        super.onPause()

        activity!!.requestedOrientation = arguments!!.getInt(originalScreenOrientationKey)

        viewModel.stop()
    }

    protected open fun onBackPressed() = viewModel.back()

    protected open fun close() = dismissAllowingStateLoss()

    fun showAllowingStateLoss(manager: FragmentManager) =
        manager.beginTransaction().add(this, tag).addToBackStack(tag).commitAllowingStateLoss()
}

val Fragment.parent: Any?
    get() = parentFragment ?: activity
