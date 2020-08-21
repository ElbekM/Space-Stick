package com.elbek.space_stick.common.extensions

import kotlin.math.abs

fun Int.modularAdd(module: Int): Int =
    if (this >= 0) this % module else module - abs(this)
