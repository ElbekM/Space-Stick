package com.elbek.space_stick.screens.sync

import android.app.Application
import com.elbek.space_stick.api.StickSyncService
import com.elbek.space_stick.common.core.BaseViewModel
import com.elbek.space_stick.common.core.commands.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SyncViewModel(private val syncApiService: StickSyncService, application: Application) :
    BaseViewModel(application) {

    private val activeUrlAddresses = mutableListOf<String>()

    val urlAddressList = SingleLiveEvent<List<String>>()

    fun init() {
        findActiveConnections()
    }

    private fun findActiveConnections() {
        for (address in DHCP_ADDRESSES) {
            launch {
                try {
                    val currentAddress = BASE_URL + address.toString()
                    syncApiService.checkConnection(currentAddress)
                    withContext(Dispatchers.Main) {
                        activeUrlAddresses.add(currentAddress)
                        urlAddressList.postValue(activeUrlAddresses)
                    }
                } catch (exception: Exception) { /* no op */ }
            }
        }
    }

    companion object {
        private const val BASE_URL = "http://192.168.43."
        private val DHCP_ADDRESSES = 1..255
    }
}
