package com.elbek.space_stick.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elbek.space_stick.common.utils.Utils.getRandomUUID

@Entity
data class ColorInfoEntity(
    @PrimaryKey val id: String = getRandomUUID(),
    val colorRgb: List<RgbEntity>?
)
