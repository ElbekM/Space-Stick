package com.elbek.space_stick.common.mvvm.commands

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

typealias Command = LiveEvent

class LiveEvent : () -> Unit {
    private val liveData = SingleMutableLiveData<Void>()

    fun observe(owner: LifecycleOwner, observer: Observer<Void>) = liveData.observe(owner, observer)

    @MainThread
    fun call() {
        liveData.value = null
    }

    override fun invoke() = call()
}