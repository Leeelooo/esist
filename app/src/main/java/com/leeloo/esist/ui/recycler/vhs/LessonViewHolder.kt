package com.leeloo.esist.ui.recycler.vhs

import android.text.format.DateUtils
import android.view.View
import com.leeloo.esist.vo.Lesson
import kotlinx.android.synthetic.main.item_lesson.view.*

class LessonViewHolder(
    private val view: View,
    private val onClick: (Long) -> Unit
) : DataViewHolder<Lesson>(view) {
    private lateinit var data: Lesson

    init {
        view.setOnClickListener { onClick(data.lessonId) }
    }

    override fun bindData(data: Lesson) {
        this.data = data
        view.lesson_subject.text = data.lessonSubject
        view.lesson_topic.text = data.lessonTopic
        view.lesson_date.text = DateUtils.formatDateTime(
            view.context,
            data.startTimestamp,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
        )
        view.lesson_time.text = "${DateUtils.formatDateTime(
            view.context,
            data.startTimestamp,
            DateUtils.FORMAT_SHOW_TIME
        )} - ${DateUtils.formatDateTime(
            view.context,
            data.endTimestamp,
            DateUtils.FORMAT_SHOW_TIME
        )}"
    }

}