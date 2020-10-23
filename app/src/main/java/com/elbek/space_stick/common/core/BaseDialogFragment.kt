package com.elbek.space_stick.common.core

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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.elbek.space_stick.common.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseDialogFragment<TViewModel : BaseViewModel> : DialogFragment(),
    CoroutineScope by MainScope(), FragmentBindings {

    private val originalScreenOrientationKey: String = ::originalScreenOrientationKey.name
    private val snackbar = Snackbar()

    protected abstract val viewModel: TViewModel
    protected open var customTheme: Int = R.style.AppTheme
    protected open val screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    override val self: Fragment
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, customTheme)
        isCancelable = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (arguments == null) {
            arguments = Bundle()
        }

        requireArguments().putInt(originalScreenOrientationKey, requireActivity().requestedOrientation)
    }

    override fun onResume() {
        super.onResume()

        requireActivity().requestedOrientation = screenOrientation
    }

    override fun onPause() {
        super.onPause()

        requireActivity().requestedOrientation = requireArguments().getInt(originalScreenOrientationKey)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        viewModel.onPermissionsResult(requestCode)
    }

    protected open fun close() = dismissAllowingStateLoss()

    protected open fun onBackPressed() = viewModel.back()

    protected open fun bindViewModel() {
        with(viewModel) {
            closeCommand.observe { close() }

            requestPermissionsCommand.observe {
                it?.let { (permissions, requestCode) ->
                    requestPermissions(permissions.toTypedArray(), requestCode)
                }
            }

            showPermissionDialogDeniedByUserCommand.observe {
                it?.let { (permission, requestCode) ->
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                        viewModel.permissionDeniedByUser(requestCode)
                    }
                }
            }

            showSnackBarCommand.observe {
                it?.let {
                    snackbar.showMessage(requireView(), requireContext(), it)
                }
            }

            showSnackBarWithActionCommand.observe {
                it?.let { (message, action) ->
                    snackbar.showMessageWithAction(requireView(), requireContext(), message, action)
                }
            }

            showAlertDialogCommand.observe {
                it?.let { dialog ->
                    AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                        .setTitle(it.title)
                        .setCancelable(it.isCancelable).let { builder ->
                            builder.setMessage(it.message)
                            builder.setPositiveButton(it.positiveButtonText) { _, _ ->
                                it.positiveAction?.invoke()
                            }
                            builder.setNegativeButton(it.negativeButtonText) { dialog, _ ->
                                dialog.dismiss()
                                it.negativeButtonAction?.invoke()
                            }
                        }
                        .create()
                        .apply { setCanceledOnTouchOutside(it.isCancelable) }
                        .show()
                }
            }
        }
    }
}
