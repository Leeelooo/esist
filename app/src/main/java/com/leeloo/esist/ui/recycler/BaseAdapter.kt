package com.leeloo.esist.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.vhs.DataViewHolder
import com.leeloo.esist.ui.recycler.vhs.EmptyViewHolder
import com.leeloo.esist.ui.recycler.vhs.ErrorViewHolder

abstract class BaseAdapter<T, VH : DataViewHolder<T>>(
    private val onRetry: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<T> = mutableListOf()
    private var isLoading: Boolean = true
    private var isError: Boolean = false

    protected abstract fun getDataViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): VH

    fun updateData(newData: List<T>, isError: Boolean, isLoading: Boolean) {
        dataList.clear()
        dataList.addAll(newData)
        this.isLoading = isLoading
        this.isError = isError
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when {
            isLoading -> 1
            isError -> 2
            dataList.size == 0 -> 3
            else -> 4 + position
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when {
            isLoading -> EmptyViewHolder(
                layoutInflater.inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
            isError -> ErrorViewHolder(
                layoutInflater.inflate(
                    R.layout.item_error,
                    parent,
                    false
                ),
                onRetry
            )
            dataList.size == 0 -> EmptyViewHolder(
                layoutInflater.inflate(
                    R.layout.item_empty,
                    parent,
                    false
                )
            )
            else -> getDataViewHolder(layoutInflater, parent)
        }
    }

    override fun getItemCount(): Int = if (isLoading || isError || dataList.isEmpty()) 1 else dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isLoading || isError) {
            return
        }
        if (dataList.isNotEmpty()) {
            (holder as VH).bindData(dataList[position])
        }
    }

}