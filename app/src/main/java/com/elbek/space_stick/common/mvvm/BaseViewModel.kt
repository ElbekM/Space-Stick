package com.elbek.space_stick.common.mvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import com.elbek.space_stick.common.mvvm.commands.TCommand
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val subscriptions: MutableList<Disposable> = mutableListOf()
    private val subscriptionsWhileVisible: MutableList<Disposable> = mutableListOf()

    protected val context: Context by lazy { getApplication<Application>() }

    val closeCommand = Command()
    val showMessageCommand = TCommand<String>()

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

    protected fun processError(
        tag: String = "SpaceStickApp",
        error: Throwable,
        display: Boolean = true
    ) {
        Log.e(tag, error.localizedMessage!!)

        if (display) showToast("Something went wrong")
    }

    protected fun showToast(message: String) = showMessageCommand.call(message)

    protected fun Disposable.addToSubscriptions() { subscriptions.add(this) }
}
