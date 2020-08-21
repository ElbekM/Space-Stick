package com.elbek.space_stick.screens.stick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.elbek.space_stick.R
import com.elbek.space_stick.screens.stick.StickViewModel
import kotlinx.android.synthetic.main.item_stick_pattern.view.*

class PatternAdapter : BaseAdapter() {

    var items: List<StickViewModel.Pattern>? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val pattern = items!![position]
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stick_pattern, null)
        view.patternIcon.setImageResource(pattern.icon)
        return view
    }

    override fun getItem(position: Int): Any = items!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items?.size ?: 0
}
