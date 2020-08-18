package com.elbek.space_stick.screens.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.Command
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(
    private val apiService: StickService,
    application: Application
) : BaseViewModel(application) {

    val launchStickScreenCommand = Command()
    val wifiSsid = MutableLiveData<String>()

    fun init(activity: FragmentActivity) {

        val connManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnected) {
            val wifiManager = activity.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                wifiSsid.value = wifiInfo.ssid
            }
        }
    }

    fun onCheckConnectionClicked() {
        launch {
            try {
                apiService.checkConnection()
                launchStickScreenCommand.call()
                withContext(Dispatchers.Main) {
                    showToast("SUCCESS")
                }
            } catch (exception: Exception) {
                processException(exception) {
                    onCheckConnectionClicked()
                }
            }
        }
    }
}

