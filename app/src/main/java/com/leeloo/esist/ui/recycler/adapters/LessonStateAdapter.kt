package com.leeloo.esist.ui.recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leeloo.esist.R
import com.leeloo.esist.ui.recycler.BaseStateAdapter
import com.leeloo.esist.ui.recycler.vhs.LessonViewHolder
import com.leeloo.esist.vo.Lesson

class LessonStateAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit
) : BaseStateAdapter<Lesson, LessonViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): LessonViewHolder =
        LessonViewHolder(
            inflater.inflate(R.layout.item_lesson, parent, false),
            onCLick
        )

}