package com.elbek.space_stick.screens.main

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.LiveEvent
import com.elbek.space_stick.common.mvvm.commands.SingleLiveEvent
import com.elbek.space_stick.common.utils.Constants
import com.elbek.space_stick.common.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val wifiSsid = SingleLiveEvent<String>()
    val connectionState = SingleLiveEvent<Boolean>()
    val launchStickScreenCommand = SingleLiveEvent<String>()
    val showRequestDialogCommand = LiveEvent()
    val launchAppSettingsCommand = LiveEvent()

    fun init() {
        //TODO: check wifi connection
        checkLocationPermission()
        checkSharedPref()
    }

    fun onCheckConnectionClicked() {
        if (wifiSsid.value.isNullOrEmpty()) {
            getWifiSsid()
            return
        }

        launch {
            try {
                connectionState.postValue(true)
                apiService.checkConnection()
                delay(1000)
                launchStickScreenCommand.postValue(wifiSsid.value)
                withContext(Dispatchers.Main) {
                    showToast("SUCCESS")
                }
            } catch (exception: Exception) {
                processException(exception) {
                    onCheckConnectionClicked()
                }
            } finally {
                connectionState.postValue(false)
            }
        }
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("WARNING")
            setMessage("To correct work app need location permission")
            setPositiveButton("Go to settings") { _, _ ->
                launchAppSettingsCommand.call()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
    }

    override fun onPermissionsResult(requestCode: Int) {
        super.onPermissionsResult(requestCode)

        if (requestCode == Constants.LOCATION_REQUEST &&
            isPermissionsGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getWifiSsid()
        } else {
            showPermissionDialogDeniedByUserCommand.call(
                Pair(Manifest.permission.ACCESS_FINE_LOCATION, requestCode)
            )
        }
    }

    override fun permissionDeniedByUser(requestCode: Int) {
        showRequestDialogCommand.call()
    }

    private fun checkLocationPermission() {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.O) {
            if (isPermissionsGranted(Manifest.permission.ACCESS_FINE_LOCATION))
                getWifiSsid()
            else
                requestPermissions(listOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.LOCATION_REQUEST)
        } else {
            getWifiSsid()
        }
    }

    private fun getWifiSsid() {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnected) {
            val wifiManager = context.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                wifiSsid.value = Utils.validateWifiSsid(wifiInfo.ssid)
            }
        } else {
            //TODO: enable wifi dialog
        }
    }

    private fun checkSharedPref() {
        //TODO: clear shared pref
        //TODO: not wifi name, use some wifi uuid, fix case with default value
        val preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        if (preferences.contains(Constants.APP_PREFERENCES_WIFI)) {
            val wifiName = preferences.getString(Constants.APP_PREFERENCES_WIFI, "")
            if (wifiName == wifiSsid.value)
                launchStickScreenCommand.postValue(wifiName)
        }
    }
}
