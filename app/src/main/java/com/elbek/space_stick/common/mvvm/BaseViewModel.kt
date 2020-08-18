package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import kotlinx.coroutines.*
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun onCleared() = coroutineContext.cancel()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()

    open fun back() = closeCommand.call()

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    protected fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    protected fun processException(exception: Exception, snackBarAction: (() -> Unit)) {
        launch(Dispatchers.Main) {
            if (exception is UnknownHostException)
                showToast("No internet connection")
            else
                showToast(exception.message ?: "Something went wrong")

        }
        exception.printStackTrace()
    }

    protected fun showToast(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
