package com.elbek.space_stick.screens.main

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import com.elbek.space_stick.R
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.extensions.toRvalue
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

    private var deviceApSsid: String? = null
    private var deviceApPass: String? = null

    private val wifiManager by lazy {
        (context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager)
    }

    val wifiSsid = SingleLiveEvent<String>()
    val connectionState = SingleLiveEvent<Boolean>()
    val launchStickScreenCommand = SingleLiveEvent<String>()
    val showRequestDialogCommand = LiveEvent()
    val launchAppSettingsCommand = LiveEvent()

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
        checkConnection()
    }

    fun onSyncClicked() {
        getDeviceAccessPointInfo()
        if (deviceApSsid.isNullOrEmpty() && deviceApPass.isNullOrEmpty()) {
            //TODO: showDialog with fields
        } else {
            sendAccessPointInfo()
        }

    }

    fun onSyncModeClicked() {
        //TODO: Switch to sync mode screen and enable access point
    }

    fun showDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.scr_main_lbl_dialog_title)
            setMessage(R.string.scr_main_lbl_dialog_location_permission)
            setPositiveButton(R.string.scr_main_lbl_dialog_settings) { _, _ ->
                launchAppSettingsCommand.call()
            }
            setNegativeButton(R.string.scr_main_lbl_dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }
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
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                wifiSsid.value = Utils.validateWifiSsid(wifiInfo.ssid)
            }
        } else {
            //TODO: enable wifi dialog
        }
    }

    private fun getDeviceAccessPointInfo() {
        //TODO: check permissions
        val methods = wifiManager.javaClass.declaredMethods
        for (method in methods) {
            if (method.name == "getWifiApConfiguration") {
                val config = method.invoke(wifiManager) as WifiConfiguration
                deviceApSsid = config.SSID
                deviceApPass = config.BSSID
            }
        }
    }

    private fun checkConnection() = launch {
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

    private fun sendAccessPointInfo() = launch {
        try {
            apiService.switchMode(deviceApSsid!!, deviceApPass!!)
        } catch (exception: Exception) {
            processException(exception) {
                showToast("Error on sending access point info")
            }
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
