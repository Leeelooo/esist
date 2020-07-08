package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.BaseAdapter
import com.leeloo.esist.ui.recycler.vhs.LessonViewHolder
import com.leeloo.esist.vo.Lesson

class MemberLessonAdapter(
    private val onClick: (Long) -> Unit
) : BaseAdapter<Lesson, LessonViewHolder>() {
    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): LessonViewHolder =
        LessonViewHolder(inflater.inflate(R.layout.item_lesson, parent, false), onClick)
}