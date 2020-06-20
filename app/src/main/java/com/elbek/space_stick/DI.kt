package com.elbek.space_stick

import android.content.Context
import com.elbek.space_stick.screens.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initDI(context: Context) {
    startKoin {
        androidContext(context)
        modules(module {

            viewModel<MainViewModel>()
        })
    }
}