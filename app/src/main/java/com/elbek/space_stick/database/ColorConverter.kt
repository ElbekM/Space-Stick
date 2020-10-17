package com.elbek.space_stick.database

import androidx.room.TypeConverter
import com.elbek.space_stick.models.entities.RgbEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class ColorConverter {
    @TypeConverter
    fun fromMutableList(tasks: MutableList<RgbEntity>?): String = Gson().toJson(tasks)

    @TypeConverter
    fun toMutableList(data: String): MutableList<RgbEntity>? =
        Gson().fromJson(data, object : TypeToken<ArrayList<RgbEntity>>() {}.type)
}
