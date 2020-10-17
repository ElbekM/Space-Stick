package com.elbek.space_stick.screens.patternSettings.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elbek.space_stick.R
import com.elbek.space_stick.models.Rgb

class CustomColorAdapter(private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<CustomColorViewHolder>() {

    private var items: List<Rgb>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomColorViewHolder =
        CustomColorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_color_pattern, parent, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: CustomColorViewHolder, position: Int) {
        items?.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    internal fun setCustomColors(colors: List<Rgb>?) {
        items = colors
        notifyDataSetChanged()
    }
}
