package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.BaseAdapter
import com.leeloo.esist.ui.recycler.vhs.MemberGroupViewHolder
import com.leeloo.esist.vo.Group

class MemberGroupsAdapter(
    private val onClick: (Long) -> Unit
) : BaseAdapter<Group, MemberGroupViewHolder>() {
    override fun getDataViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): MemberGroupViewHolder = MemberGroupViewHolder(
        inflater.inflate(R.layout.item_add, parent, false),
        onClick = onClick
    )
}