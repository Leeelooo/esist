package com.leeloo.esist.ui.recycler.vhs

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.leeloo.esist.R
import com.leeloo.esist.vo.Member
import kotlinx.android.synthetic.main.item_member_attendance.view.*

class AttendanceViewHolder(
    private val view: View,
    private val onClick: (Long) -> Unit,
    private val onSetAttendance: (Long) -> Unit,
    private val onRemoveAttendance: (Long) -> Unit
) : RecyclerView.ViewHolder(view) {
    private lateinit var data: Member

    init {
        view.setOnClickListener {
            onClick(data.memberId)
        }
        view.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) onSetAttendance(data.memberId)
            else onRemoveAttendance(data.memberId)
        }
    }

    fun bind(data: Member, isChecked: Boolean) {
        this.data = data
        view.checkbox.isChecked = isChecked
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