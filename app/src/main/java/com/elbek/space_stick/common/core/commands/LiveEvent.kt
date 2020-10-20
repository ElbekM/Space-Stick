package com.elbek.space_stick.common.core.commands

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class LiveEvent : () -> Unit {
    private val liveData = SingleMutableLiveData<Void>()

    fun observe(owner: LifecycleOwner, observer: Observer<Void>) = liveData.observe(owner, observer)

    @MainThread
    fun call() {
        liveData.value = null
    }

    override fun invoke() = call()
}
