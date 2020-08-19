package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import com.elbek.space_stick.common.mvvm.commands.TCommand
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun onCleared() = coroutineContext.cancel()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()
    val showSnackBarWithActionCommand = TCommand<Pair<String, (() -> Unit)>>()
    val showSnackBarCommand = TCommand<String>()

    open fun back() = closeCommand.call()

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
                is UnknownHostException -> {
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
