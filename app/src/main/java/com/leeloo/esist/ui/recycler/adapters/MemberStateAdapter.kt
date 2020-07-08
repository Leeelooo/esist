package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.BaseStateAdapter
import com.leeloo.esist.ui.recycler.vhs.MemberViewHolder
import com.leeloo.esist.vo.Member

class MemberStateAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit
) : BaseStateAdapter<Member, MemberViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): MemberViewHolder =
        MemberViewHolder(
            inflater.inflate(R.layout.item_member, parent, false),
            onCLick
        )

}