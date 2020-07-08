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
        view.set_attendance.setOnClickListener { onSetAttendance(data.memberId) }
        view.remove_attendance.setOnClickListener { onRemoveAttendance(data.memberId) }
    }

    fun bind(data: Member, isChecked: Boolean) {
        this.data = data
        view.set_attendance.isEnabled = !isChecked
        view.remove_attendance.isEnabled = isChecked
        view.set_attendance.isChecked = isChecked
        view.remove_attendance.isChecked = !isChecked
        view.member_attendance_group.addOnButtonCheckedListener { group, checkedId, isCheck ->
            if (isCheck) {
                view.set_attendance.isEnabled = checkedId != R.id.set_attendance
                view.remove_attendance.isEnabled = checkedId == R.id.set_attendance
            }
        }
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