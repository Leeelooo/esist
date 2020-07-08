package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import com.leeloo.esist.vo.Group
import kotlinx.android.synthetic.main.item_add.view.*

class MemberGroupViewHolder(
    private val view: View,
    private val onClick: (Long) -> Unit
) : DataViewHolder<Group>(view) {
    private lateinit var data: Group

    init {
        view.setOnClickListener { onClick(data.groupId) }
    }

    override fun bindData(data: Group) {
        this.data = data
        view.chip.text = data.groupName
    }

}