package com.elbek.space_stick.common.extensions

import android.view.View
import androidx.core.os.postDelayed

const val RIPPLE_CLICK_DELAY = 1500L

fun View.onRippleClick(delay: Long = RIPPLE_CLICK_DELAY, listener: (View) -> Unit) = setOnClickListener {
    if (isEnabled) {
        isEnabled = false
        listener(this)
        handler?.postDelayed(delay) {
            isEnabled = true
        }
    }
}
