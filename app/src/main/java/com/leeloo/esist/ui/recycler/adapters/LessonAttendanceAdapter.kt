package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.vhs.AttendanceViewHolder
import com.leeloo.esist.ui.recycler.vhs.EmptyViewHolder
import com.leeloo.esist.vo.Member

class LessonAttendanceAdapter(
    private val onClick: (Long) -> Unit,
    private val onSetAttendance: (Long) -> Unit,
    private val onRemoveAttendance: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<Member> = mutableListOf()
    private val checkedList: MutableSet<Member> = mutableSetOf()

    override fun getItemViewType(position: Int): Int =
        when (dataList.size) {
            0 -> 3
            else -> 4 + position
        }

    fun addData(data: List<Member>, checked: List<Member>) {
        dataList.clear()
        dataList.addAll(data)
        checkedList.clear()
        checkedList.addAll(checked)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (dataList.size) {
            0 -> EmptyViewHolder(
                layoutInflater.inflate(
                    R.layout.item_add_empty,
                    parent,
                    false
                )
            )
            else -> AttendanceViewHolder(
                layoutInflater.inflate(
                    R.layout.item_member_attendance,
                    parent,
                    false
                ),
                onClick,
                onSetAttendance,
                onRemoveAttendance
            )
        }
    }

    override fun getItemCount(): Int = if (dataList.isEmpty()) 1 else dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AttendanceViewHolder) {
            holder.bind(dataList[position], checkedList.contains(dataList[position]))
        }
    }

}