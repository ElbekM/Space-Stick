package com.elbek.space_stick.screens.sync

import android.app.Application
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.common.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class SyncViewModel(private val apiService: StickService, application: Application) :
    BaseViewModel(application) {

    private val activeUrlAddresses = mutableListOf<String>()

    fun init() {
        findActiveConnections()
    }

    private fun findActiveConnections() {
        for (address in DHTP_ADDRESSES) {
            launch {
                try {
                    val currentAddress = BASE_URL + address.toString() + CHECK_API
                    val isActiveConnection = checkConnection(currentAddress)
                    if (isActiveConnection) {
                        withContext(Dispatchers.Main) {
                            activeUrlAddresses.add(currentAddress)
                        }
                    }
                } catch (exception: Exception) {
                    loggError(exception)
                }
            }
        }
    }

    private fun checkConnection(address: String): Boolean =
        (URL(address).openConnection() as HttpURLConnection).run {
            connect()
            responseCode == HttpURLConnection.HTTP_OK
        }

    companion object {
        private const val BASE_URL = "http://192.168.43."
        private const val CHECK_API = "/api/check"
        private val DHTP_ADDRESSES = 1..255
    }
}
