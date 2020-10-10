package com.elbek.space_stick.screens.stick.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elbek.space_stick.models.Pattern
import kotlinx.android.synthetic.main.item_stick_pattern.view.*

class PatternViewHolder(
    view: View,
    private val onItemClicked: (Int) -> Unit,
    private val onItemLongClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(item: Pattern) {
        with(itemView) {
            setOnClickListener { onItemClicked(item.position) }
            setOnLongClickListener {
                onItemLongClicked(item.position)
                return@setOnLongClickListener true
            }

            patternIcon.setImageResource(item.icon)
        }
    }
}
