package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.leeloo.esist.R
import com.leeloo.esist.vo.Member
import kotlinx.android.synthetic.main.item_member.view.*

class MemberViewHolder(
    private val view: View,
    private val onCLick: (Long) -> Unit
) : DataViewHolder<Member>(view) {
    private lateinit var member: Member

    init {
        view.setOnClickListener { onCLick(member.memberId) }
    }

    override fun bindData(data: Member) {
        this.member = data
        view.item_member_name.text = "${data.firstName} ${data.lastName}"
        view.item_member_icon.setImageDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.ic_member_circle
            )
        )
        DrawableCompat.setTint(
            view.item_member_icon.drawable,
            ContextCompat.getColor(view.context, data.memberColor)
        )
    }

}