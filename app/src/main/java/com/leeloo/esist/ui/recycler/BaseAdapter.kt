package com.leeloo.esist.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.vhs.DataViewHolder
import com.leeloo.esist.ui.recycler.vhs.EmptyViewHolder

abstract class BaseAdapter<T, VH : DataViewHolder<T>> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<T> = mutableListOf()

    protected abstract fun getDataViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): VH

    fun updateData(newData: List<T>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when {
            dataList.size == 0 -> 3
            else -> 4 + position
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when {
            dataList.size == 0 -> EmptyViewHolder(
                layoutInflater.inflate(
                    R.layout.item_add_empty,
                    parent,
                    false
                )
            )
            else -> getDataViewHolder(layoutInflater, parent)
        }
    }

    override fun getItemCount(): Int =
        if (dataList.isEmpty()) 1 else dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (dataList.isNotEmpty()) {
            (holder as VH).bindData(dataList[position])
        }
    }

}