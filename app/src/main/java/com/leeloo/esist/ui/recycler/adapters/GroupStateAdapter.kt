package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.BaseStateAdapter
import com.leeloo.esist.ui.recycler.vhs.GroupViewHolder
import com.leeloo.esist.vo.Group

class GroupStateAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit
) : BaseStateAdapter<Group, GroupViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): GroupViewHolder =
        GroupViewHolder(
            inflater.inflate(R.layout.item_group, parent, false),
            onCLick
        )

}