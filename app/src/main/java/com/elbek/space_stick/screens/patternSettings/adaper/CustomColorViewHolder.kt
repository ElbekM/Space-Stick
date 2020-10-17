package com.elbek.space_stick.screens.patternSettings.adaper

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elbek.space_stick.common.utils.Utils.rgbToHex
import com.elbek.space_stick.models.Rgb
import kotlinx.android.synthetic.main.item_color_pattern.view.*

class CustomColorViewHolder(
    view: View,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(item: Rgb) {
        with(itemView.colorPatternMaterialButton) {
            setOnClickListener { onItemClicked(adapterPosition) }
            backgroundTintList = ColorStateList.valueOf((Color.parseColor(rgbToHex(item))))
        }
    }
}
