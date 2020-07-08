package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseAction

sealed class LessonDetailsAction : BaseAction {
    object InitialAction : LessonDetailsAction()

    data class LoadLessonDetailsAction(
        val lessonId: Long
    ) : LessonDetailsAction()

    data class SetAttendanceAction(
        val lessonId: Long,
        val memberId: Long
    ) : LessonDetailsAction()

    data class RemoveAttendanceAction(
        val lessonId: Long,
        val memberId: Long
    ) : LessonDetailsAction()

}