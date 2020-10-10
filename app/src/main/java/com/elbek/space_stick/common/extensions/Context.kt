package com.elbek.space_stick.common.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat

fun Context.getColorCompat(@ColorRes colorRes: Int) =
    ContextCompat.getColor(this, colorRes)

val Context.appVersionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.appVersionCode: Long
    get() = PackageInfoCompat.getLongVersionCode(packageManager.getPackageInfo(packageName, 0))
