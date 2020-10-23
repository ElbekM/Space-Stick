package com.elbek.space_stick.database

import com.elbek.space_stick.models.Rgb
import com.elbek.space_stick.models.entities.RgbEntity
import com.elbek.space_stick.models.entities.toModel

class ColorDatabaseProvider(private val database: ColorDatabase) {

    suspend fun getCustomColors(): MutableList<Rgb>? =
        database.getColorDbDao()
            .getAll()?.toModel()

    suspend fun addColorToDatabase(colorList: Rgb) =
        database.getColorDbDao()
            .addNewColor(RgbEntity.createRgbEntity(colorList))
}
