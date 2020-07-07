package com.leeloo.esist.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.vhs.EmptyViewHolder

class AddAdapter(
    private val onCLick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<Pair<String, Long>> = mutableListOf()

    override fun getItemViewType(position: Int): Int =
        when (dataList.size) {
            0 -> 3
            else -> 4 + position
        }

    fun addData(data: List<Pair<String, Long>>) {
        if (dataList.size == data.size) {
            return
        }
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (dataList.size) {
            0 -> EmptyViewHolder(
                layoutInflater.inflate(
                    R.layout.item_add_empty,
                    parent,
                    false
                )
            )
            else -> AddViewHolder(
                layoutInflater.inflate(
                    R.layout.item_add,
                    parent,
                    false
                ),
                onCLick
            )
        }
    }

    override fun getItemCount(): Int = if (dataList.isEmpty()) 1 else dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AddViewHolder) {
            holder.bind(dataList[position])
        }
    }

}
