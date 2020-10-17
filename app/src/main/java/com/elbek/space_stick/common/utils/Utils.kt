package com.elbek.space_stick.common.utils

import com.elbek.space_stick.SpaceStickApplication
import com.elbek.space_stick.models.Rgb
import java.util.UUID

object Utils {

    const val COLUMN_WIDTH_DP = 140f

    @JvmStatic
    fun validateWifiSsid(wifiSsid: String): String =
        if (wifiSsid.isEmpty() || wifiSsid == Constants.UNKNOWN_WIFI_SSID)
            Constants.DEFAULT_WIFI_NAME
        else
            wifiSsid.replace("\"","")

    @JvmStatic
    fun calculateNumberOfColumns(columnWidthDp: Float = COLUMN_WIDTH_DP): Int {
        val displayMetrics = SpaceStickApplication.applicationContext().resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    @JvmStatic
    fun rgbToHex(rgb: Rgb): String = String.format("#%02x%02x%02x", rgb.r, rgb.g, rgb.b)

    fun getRandomUUID() = UUID.randomUUID().toString()
}
