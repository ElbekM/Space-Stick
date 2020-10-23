package com.elbek.space_stick.screens.main

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import com.elbek.space_stick.R
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.dialog.DialogRequest
import com.elbek.space_stick.common.extensions.toRvalue
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.LiveEvent
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
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
                    showToast(R.string.scr_any_msg_success.toRvalue())
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
        showAlertDialog(
            DialogRequest(
                title = R.string.scr_main_lbl_dialog_title.toRvalue(),
                message = R.string.scr_main_lbl_dialog_location_permission.toRvalue(),
                positiveButtonText = R.string.scr_main_lbl_dialog_settings.toRvalue(),
                negativeButtonText = R.string.scr_main_lbl_dialog_cancel.toRvalue(),
                positiveAction = { launchAppSettingsCommand.call() }
            )
        )
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
