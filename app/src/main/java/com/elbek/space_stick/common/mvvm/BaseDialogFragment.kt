package com.elbek.space_stick.common.mvvm

import com.elbek.space_stick.R

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                // Workaround to avoid multiple calls of KeyListener.
                // It prevents closing multiple fragments at one time.
                Handler(Looper.getMainLooper()).postDelayed({ onBackPressed() }, 100)
                return@OnKeyListener true

            } else {
                return@OnKeyListener false
            }
        })
    }

    protected open fun bindViewModel() {

        viewModel.closeCommand.observe(this, Observer {
            close()
        })

        viewModel.showMessageCommand.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

    }

    protected open fun close() = dismissAllowingStateLoss()

    protected open fun onBackPressed() = viewModel.back()
}

fun DialogFragment.showAllowingStateLoss(fm: FragmentManager, tag: String = this::class.java.name) =
    fm.beginTransaction()
        .add(this, tag)
        .addToBackStack(tag)
        .commitAllowingStateLoss()

val Fragment.parent: Any?
    get() = parentFragment ?: activity

inline fun <reified T> Fragment.castParent(): T? = parent as? T