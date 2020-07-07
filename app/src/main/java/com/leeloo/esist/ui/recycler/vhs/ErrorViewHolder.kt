package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_error.view.*

class ErrorViewHolder(
    view: View,
    onRetry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.retry_button.setOnClickListener { onRetry() }
    }

}