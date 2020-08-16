package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import com.elbek.space_stick.common.mvvm.commands.TCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun onCleared() = coroutineContext.cancel()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()
    val showMessageCommand = TCommand<String>()

    open fun back() = closeCommand.call()

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    protected fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    protected fun processException(
        tag: String = "SpaceStickApp",
        error: Throwable,
        display: Boolean = true
    ) {
        Log.e(tag, error.localizedMessage!!)

        if (display) showToast("Something went wrong")
    }

    protected fun showToast(message: String) = showMessageCommand.call(message)
}
