package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import com.leeloo.esist.vo.Group
import kotlinx.android.synthetic.main.item_group.view.*

class GroupViewHolder(
    private val view: View,
    private val onClick: (View, Long) -> Unit
) : DataViewHolder<Group>(view) {
    private lateinit var data: Group

    init {
        view.setOnClickListener { onClick(it, data.groupId) }
    }

    override fun bindData(data: Group) {
        this.data = data
        view.item_group_name.text = data.groupName
    }

}