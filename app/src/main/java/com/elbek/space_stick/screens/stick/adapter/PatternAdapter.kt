package com.elbek.space_stick.screens.stick.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elbek.space_stick.R
import com.elbek.space_stick.models.Pattern

class PatternAdapter(
    private val onItemClicked: (Int) -> Unit,
    private val onItemLongClicked: (Int) -> Unit
) : RecyclerView.Adapter<PatternViewHolder>() {

    private var items: List<Pattern>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternViewHolder =
        PatternViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stick_pattern, parent, false),
            onItemClicked,
            onItemLongClicked
        )

    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
        holder.bind(items!![position])
    }

    override fun getItemCount(): Int = items?.size ?: 0

    internal fun setPatterns(patterns: List<Pattern>) {
        items = patterns
        notifyDataSetChanged()
    }
}
