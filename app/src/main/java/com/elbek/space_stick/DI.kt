package com.elbek.space_stick

import android.content.Context
import com.elbek.space_stick.api.ApiController
import com.elbek.space_stick.api.ApiServiceProvider
import com.elbek.space_stick.api.StickService
import com.elbek.space_stick.database.ColorDatabase
import com.elbek.space_stick.database.ColorDatabaseProvider
import com.elbek.space_stick.screens.main.MainViewModel
import com.elbek.space_stick.screens.patternSettings.PatternSettingsViewModel
import com.elbek.space_stick.screens.patternSettings.RgbSettingsViewModel
import com.elbek.space_stick.screens.settings.SettingsViewModel
import com.elbek.space_stick.screens.stick.StickViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.single

fun initDI(context: Context) {
    startKoin {
        androidContext(context)
        modules(module {
            single<ApiServiceProvider>()
            single<ColorDatabaseProvider>()
            single<StickService>()

            single { ColorDatabase.newInstance(context) }
            single {
                this.get<ApiServiceProvider>(ApiServiceProvider::class, null, null)
                    .spaceStickRetrofit
                    .create(ApiController::class.java)
            }

            viewModel<MainViewModel>()
            viewModel<StickViewModel>()
            viewModel<SettingsViewModel>()
            viewModel<RgbSettingsViewModel>()
            viewModel<PatternSettingsViewModel>()
        })
    }
}
