package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseIntent

sealed class LessonDetailsIntent : BaseIntent<LessonDetailsAction> {

    object InitialIntent : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction = LessonDetailsAction.InitialAction
    }

    class LoadLessonDetailsIntent(
        private val lessonId: Long
    ) : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.LoadLessonDetailsAction(lessonId)
    }

    class ReloadLessonDetailsIntent(
        private val lessonId: Long
    ) : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.LoadLessonDetailsAction(lessonId)
    }

    class SetAttendanceToMemberIntent(
        private val lessonId: Long,
        private val memberId: Long
    ) : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.SetAttendanceAction(lessonId, memberId)
    }

    class RemoveAttendanceToMemberIntent(
        private val lessonId: Long,
        private val memberId: Long
    ) : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.RemoveAttendanceAction(lessonId, memberId)
    }

    class GetAttendanceIntent(
        private val lessonId: Long
    ) : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.AttendanceAction(lessonId)
    }

    object DismissDialogIntent : LessonDetailsIntent() {
        override fun convertToAction(): LessonDetailsAction =
            LessonDetailsAction.DismissAction
    }

}