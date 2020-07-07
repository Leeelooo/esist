package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseIntent

sealed class LessonIntent : BaseIntent<LessonAction> {

    object InitialIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.LoadLessonsAction
    }

    object ReloadIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.LoadLessonsAction
    }

    object OpenDialogIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.OpenDialogAction
    }

    object DismissDialogIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.DismissDialogAction
    }

    class GroupToAddSelectedIntent(
        private val groupId: Long
    ) : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.GroupToAddAction(groupId)
    }

    class CreateGroupIntent(
        private val subjectName: String,
        private val topicName: String,
        private val startTime: Long,
        private val finishTime: Long,
        private val homework: String?,
        private val book: String?
    ) : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction
            .AddLessonAction(
                subjectName = subjectName,
                topicName = topicName,
                startTime = startTime,
                finishTime = finishTime,
                homework = homework,
                book = book
            )
    }

}