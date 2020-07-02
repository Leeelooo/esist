package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseAction

sealed class LessonAction : BaseAction {

    data class LoadLessonsAction(
        val phrase: String
    ) : LessonAction()

    object OpenDialogAction : LessonAction()

    object DismissDialogAction : LessonAction()

    data class AddLessonAction(
        val subjectName: String,
        val topicName: String,
        val startTime: Long,
        val finishTime: Long,
        val homework: String?
    ) : LessonAction()

}