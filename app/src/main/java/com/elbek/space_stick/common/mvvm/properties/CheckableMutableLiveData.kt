package com.elbek.space_stick.common.mvvm.properties

typealias Checkable = CheckableMutableLiveData

class CheckableMutableLiveData(defaultValue: Boolean = false) : WrappedMutableLiveData<Boolean>(defaultValue)