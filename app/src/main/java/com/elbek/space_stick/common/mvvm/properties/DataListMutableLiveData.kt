package com.elbek.space_stick.common.mvvm.properties

typealias DataList<T> = DataListMutableLiveData<T>

class DataListMutableLiveData<T>(defaultValue: MutableList<T> = mutableListOf()) : WrappedMutableLiveData<MutableList<T>>(defaultValue)