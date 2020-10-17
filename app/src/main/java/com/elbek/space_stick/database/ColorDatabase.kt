package com.elbek.space_stick.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elbek.space_stick.models.entities.ColorInfoEntity

@Database(entities = [ColorInfoEntity::class], exportSchema = false, version = 1)
@TypeConverters(ColorConverter::class)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun getColorDbDao(): ColorInfoDao

    companion object {
        fun newInstance(context: Context): ColorDatabase =
            Room.databaseBuilder(context.applicationContext, ColorDatabase::class.java, "colors.db")
                .build()
    }
}
