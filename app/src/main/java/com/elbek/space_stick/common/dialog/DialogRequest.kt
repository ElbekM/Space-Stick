package com.elbek.space_stick.common.dialog

data class DialogRequest(
    val title: String = "",
    val message: String = "",
    val positiveButtonText: String = "",
    val negativeButtonText: String = "",
    val positiveAction: (() -> Unit)? = null,
    val negativeButtonAction: (() -> Unit)? = null,
    val isCancelable: Boolean = true
)
