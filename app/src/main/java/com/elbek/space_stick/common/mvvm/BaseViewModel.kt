package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import com.elbek.space_stick.common.mvvm.commands.TCommand
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun onCleared() = coroutineContext.cancel()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()
    val showSnackBarWithActionCommand = TCommand<Pair<String, (() -> Unit)>>()
    val showSnackBarCommand = TCommand<String>()
    val requestPermissionsCommand = TCommand<Pair<List<String>, Int>>()
    val showPermissionDialogDeniedByUserCommand = TCommand<Pair<String, Int>>()

    open fun back() = closeCommand.call()
    open fun onPermissionsResult(requestCode: Int) { }
    open fun permissionDeniedByUser(requestCode: Int) { }

    protected fun isPermissionsGranted(vararg permissions: String): Boolean =
        permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    protected fun requestPermissions(permissions: List<String>, requestCode: Int)
            = requestPermissionsCommand.call(Pair(permissions, requestCode))

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    protected fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    protected fun showSnackBar(message: String) {
        launch(Dispatchers.Main) {
            showSnackBarCommand.postValue(message)
        }
    }

    protected fun showSnackBarWithAction(message: String, action: () -> Unit) {
        launch(Dispatchers.Main) {
            showSnackBarWithActionCommand.postValue(Pair(message, action))
        }
    }

    protected fun showToast(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    protected fun processException(exception: Exception, action: (() -> Unit)) {
        launch(Dispatchers.Main) {
            when (exception) {
                is ConnectException -> {
                    showSnackBarWithAction("No internet connection") {
                        action()
                    }
                }
                is SocketTimeoutException -> {
                    showSnackBarWithAction("Failed connection") {
                        action()
                    }
                }
                else -> showToast("Something went wrong")
            }
        }
        exception.printStackTrace()
    }
}
