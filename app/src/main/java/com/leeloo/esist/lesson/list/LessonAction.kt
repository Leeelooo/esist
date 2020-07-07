package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseAction

sealed class LessonAction : BaseAction {

    object LoadLessonsAction : LessonAction()

    object OpenDialogAction : LessonAction()

    object DismissDialogAction : LessonAction()

    data class GroupToAddAction(
        val groupId: Long
    ) : LessonAction()

    data class AddLessonAction(
        val subjectName: String,
        val topicName: String,
        val startTime: Long,
        val finishTime: Long,
        val homework: String?,
        val book: String?
    ) : LessonAction()

}