package com.elbek.space_stick.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elbek.space_stick.models.entities.RgbEntity

@Database(entities = [RgbEntity::class], exportSchema = false, version = 1)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun getColorDbDao(): CustomColorsDao

    companion object {
        fun newInstance(context: Context): ColorDatabase =
            Room.databaseBuilder(context.applicationContext, ColorDatabase::class.java, "colors.db")
                .build()
    }
}
