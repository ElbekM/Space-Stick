package com.elbek.space_stick.common.mvvm.properties

typealias Text = TextMutableLiveData

class TextMutableLiveData(defaultValue: String = "") : WrappedMutableLiveData<String>(defaultValue)