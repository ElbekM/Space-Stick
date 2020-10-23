package com.elbek.space_stick.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elbek.space_stick.common.utils.Utils.getRandomUUID
import com.elbek.space_stick.models.Rgb

@Entity
data class RgbEntity(
    @PrimaryKey val id: String = getRandomUUID(),
    val r: Int,
    val g: Int,
    val b: Int
) {
    constructor(rgb: Rgb) : this(
        r = rgb.r,
        g = rgb.g,
        b = rgb.b
    )

    companion object {
        fun createRgbEntity(rgbList: Rgb): RgbEntity = RgbEntity(rgbList)
    }
}

fun List<RgbEntity>.toModel(): MutableList<Rgb> = map { Rgb(it.r, it.g, it.b) }.toMutableList()
