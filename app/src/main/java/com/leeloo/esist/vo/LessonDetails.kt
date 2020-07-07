package com.leeloo.esist.vo

data class LessonDetails(
    val lessonId: Long = 0L,
    val lessonSubject: String,
    val lessonTopic: String,
    val lessonHomework: String?,
    val lessonColor: LessonColor,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val lessonBook: String?,
    val lessonGroups: List<Group>
)
