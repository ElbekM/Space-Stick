package com.elbek.space_stick.common.extensions

import com.elbek.space_stick.SpaceStickApplication
import kotlin.math.abs

fun Int.toRvalue(): String = SpaceStickApplication.applicationContext().getString(this)

fun Int.modularAdd(module: Int): Int =
    if (this >= 0) this % module else module - abs(this)
