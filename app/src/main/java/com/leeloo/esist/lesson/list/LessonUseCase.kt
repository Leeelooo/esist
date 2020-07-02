package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseUseCase
import com.leeloo.esist.group.list.GroupModelState

interface LessonUseCase : BaseUseCase<GroupModelState> {
    fun getFilteredLessons(phrase: String)

    fun openDialog()
    fun dismissDialog()

    fun addLesson(
        subjectName: String,
        topicName: String,
        startTime: Long,
        finishTime: Long,
        homework: String?
    )
}