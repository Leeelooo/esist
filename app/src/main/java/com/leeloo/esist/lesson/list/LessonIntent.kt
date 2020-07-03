package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseIntent

sealed class LessonIntent : BaseIntent<LessonAction> {

    object InitialIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.LoadLessonsAction("")
    }

    class ReloadIntent(
        private val phrase: String
    ) : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.LoadLessonsAction(phrase)
    }

    class ChangeFilterPhraseIntent(
        private val phrase: String
    ) : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.LoadLessonsAction(phrase)
    }

    object OpenDialogIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.OpenDialogAction
    }

    object DismissDialogIntent : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction.DismissDialogAction
    }

    class CreateGroupIntent(
        private val subjectName: String,
        private val topicName: String,
        private val startTime: Long,
        private val finishTime: Long,
        private val homework: String?
    ) : LessonIntent() {
        override fun convertToAction(): LessonAction = LessonAction
            .AddLessonAction(
                subjectName = subjectName,
                topicName = topicName,
                startTime = startTime,
                finishTime = finishTime,
                homework = homework
            )
    }

}