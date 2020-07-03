package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseUseCase

interface LessonDetailsUseCase : BaseUseCase<LessonDetailsModelState> {
    fun loadLessonDetails(lessonId: Long)
    fun setAttendance(lessonId: Long, memberId: Long)
    fun removeAttendance(lessonId: Long, memberId: Long)
}