package com.leeloo.esist.vo

import java.net.URI

//TODO: members attendance infomation
data class LessonDetails(
    val lessonId: Long,
    val lessonSubject: String,
    val lessonTopic: String,
    val lessonHomework: String?,
    val lessonColor: LessonColor,
    val lessonTeacher: Member,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val lessonBooks: List<URI>,
    val lessonGroups: List<Group>
)
