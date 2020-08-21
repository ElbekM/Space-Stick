package com.elbek.space_stick.common.utils

object Utils {

    @JvmStatic
    fun validateWifiSsid(wifiSsid: String): String =
        if (wifiSsid.isEmpty() || wifiSsid == Constants.UNKNOWN_WIFI_SSID)
            Constants.DEFAULT_WIFI_NAME
        else
            wifiSsid.replace("\"","")
}
