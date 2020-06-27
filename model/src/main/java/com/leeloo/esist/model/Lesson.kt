package com.leeloo.esist.model

import java.net.URI

data class Lesson(
    val lessonId: Long,
    val lessonSubject: String,
    val lessonTopic: String,
    val lessonHomework: String,
    val lessonColor: LessonColor,
    val lessonTeacher: Member,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val lessonBooks: List<URI>,
    val lessonGroups: List<Group>,
    val lessonAttendance: List<Member>
)