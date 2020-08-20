package com.elbek.space_stick.screens.main

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import androidx.lifecycle.MutableLiveData
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.mvvm.BaseViewModel
import com.elbek.space_stick.common.mvvm.commands.LiveEvent
import com.elbek.space_stick.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val wifiSsid = MutableLiveData<String>()
    val launchStickScreenCommand = MutableLiveData<String>()
    val showRequestDialogCommand = LiveEvent()
    val launchAppSettingsCommand = LiveEvent()

    fun init() {

        checkLocationPermission()
        checkSharedPref()
    }

    fun onCheckConnectionClicked() {
        launch {
            try {
                apiService.checkConnection()
                launchStickScreenCommand.postValue(wifiSsid.value)
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
            getWifiName()
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
        if (isPermissionsGranted(Manifest.permission.ACCESS_FINE_LOCATION))
            getWifiName()
        else
            requestPermissions(listOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.LOCATION_REQUEST)
    }

    private fun getWifiName() {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnected) {
            val wifiManager = context.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                wifiSsid.value = wifiInfo.ssid
            }
        }
    }

    private fun checkSharedPref() {
        //TODO: not wifi name, use some wifi uuid
        val preferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
        if (preferences.contains(Constants.APP_PREFERENCES_WIFI)) {
            val wifiName = preferences.getString(Constants.APP_PREFERENCES_WIFI, "")
            if (wifiName == wifiSsid.value)
                launchStickScreenCommand.postValue(wifiName)
        }
    }
}
