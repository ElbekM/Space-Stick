package com.elbek.space_stick

import android.app.Application
import android.content.Context

class SpaceStickApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        initDI(applicationContext)
    }

    companion object {

        private var instance: SpaceStickApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}
