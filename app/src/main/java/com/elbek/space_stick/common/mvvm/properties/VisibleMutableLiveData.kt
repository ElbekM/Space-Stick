package com.elbek.space_stick.common.mvvm.properties

typealias Visible = VisibleMutableLiveData

class VisibleMutableLiveData(defaultValue: Boolean = false) : WrappedMutableLiveData<Boolean>(defaultValue)