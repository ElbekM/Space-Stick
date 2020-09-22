package com.elbek.space_stick.common.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes colorRes: Int) =
    ContextCompat.getColor(this, colorRes)
