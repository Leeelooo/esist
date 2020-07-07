package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class DataViewHolder<T>(
    view: View
) : RecyclerView.ViewHolder(view) {
    abstract fun bindData(data: T)
}