package com.elbek.space_stick.models

import androidx.annotation.ColorRes
import com.elbek.space_stick.R

enum class WifiStatus(val value: String, @ColorRes val color: Int) {
    CONNECTED("Connected", R.color.green),
    FAILED("Connection failed", R.color.red)
}
