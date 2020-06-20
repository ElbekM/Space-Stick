package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.annotation.StringRes
import com.elbek.space_stick.common.mvvm.commands.Command
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val subscriptions: MutableList<Disposable> = mutableListOf()
    private val subscriptionsWhileVisible: MutableList<Disposable> = mutableListOf()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()

    open fun destroy() {
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
    }

    open fun back() = closeCommand.call()

    open fun start() {}

    open fun stop() {
        subscriptionsWhileVisible.forEach { it.dispose() }
        subscriptionsWhileVisible.clear()
    }
}

fun BaseViewModel.getString(@StringRes resId: Int, vararg formatArgs: Any): String =
    this.getApplication<Application>().getString(resId, *formatArgs)