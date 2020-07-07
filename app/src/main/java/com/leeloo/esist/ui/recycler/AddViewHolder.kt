package com.leeloo.esist.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_add.view.*

class AddViewHolder(
    private val view: View,
    onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(view) {
    private lateinit var data: Pair<String, Long>
    private var isChecked = false

    init {
        view.chip.isCheckable = true
        view.setOnClickListener {
            isChecked = !isChecked
            view.chip.isChecked = isChecked
            onClick(data.second)
        }
    }

    fun bind(data: Pair<String, Long>) {
        isChecked = false
        view.chip.isChecked = isChecked
        this.data = data
        view.chip.text = data.first
    }

}