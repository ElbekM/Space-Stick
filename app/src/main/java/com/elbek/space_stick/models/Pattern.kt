package com.elbek.space_stick.models

import androidx.annotation.DrawableRes

data class Pattern(
    val name: String,
    val position: Int,
    @DrawableRes val icon:  Int
)
