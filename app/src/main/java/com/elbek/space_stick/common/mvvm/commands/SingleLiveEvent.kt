package com.elbek.space_stick.common.mvvm.commands

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

typealias TCommand<T> = SingleLiveEvent<T>

class SingleLiveEvent<T> : (T) -> Unit {

    private val liveData = SingleMutableLiveData<T>()

    fun observe(owner: LifecycleOwner, observer: Observer<in T>) = liveData.observe(owner, observer)

    override fun invoke(arg: T) = call(arg)

    @MainThread
    fun call(value: T) {
        liveData.value = value
    }
}