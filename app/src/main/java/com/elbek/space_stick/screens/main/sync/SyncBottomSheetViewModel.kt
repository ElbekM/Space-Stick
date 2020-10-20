package com.elbek.space_stick.screens.main.sync

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SyncBottomSheetViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    val wifiSsidField = SingleLiveEvent<String>()
    val wifiPasswordField = SingleLiveEvent<String>()

    fun init(wifiSsid: String?, wifiPassword: String?) {
        wifiSsidField.value = wifiSsid
        wifiPasswordField.value = wifiPassword
    }

    fun onSendWifiDataClicked(wifiSsid: String?, wifiPassword: String?) {
        if (wifiSsid.isNullOrEmpty() || wifiPassword.isNullOrEmpty()) {
            //TODO: Update state
        } else {
            sendAccessPointInfo(wifiSsid, wifiPassword)
        }
    }

    private fun sendAccessPointInfo(wifiSsid: String, wifiPassword: String) = launch {
        try {
            //TODO: Progress bar state
            apiService.switchMode(wifiSsid, wifiPassword)
            withContext(Dispatchers.Main) {
                closeCommand.call()
            }
        } catch (exception: Exception) {
            processException(exception) {
                showToast("Error on sending access point info")
            }
        }
    }
}
