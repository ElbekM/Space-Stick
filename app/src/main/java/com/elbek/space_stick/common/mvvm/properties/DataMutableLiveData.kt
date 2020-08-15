package com.elbek.space_stick.common.mvvm.properties

import androidx.lifecycle.MutableLiveData

typealias Data<T> = DataMutableLiveData<T>

typealias StateData<T> = MutableLiveData<T>

class DataMutableLiveData<T>(defaultValue: T) : WrappedMutableLiveData<T>(defaultValue)