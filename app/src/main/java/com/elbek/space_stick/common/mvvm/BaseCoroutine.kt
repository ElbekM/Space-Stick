package com.elbek.space_stick.common.mvvm

import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseCoroutine : DialogFragment(), CoroutineScope by MainScope() {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
}