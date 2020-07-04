package com.leeloo.esist.vo

data class Lesson(
    val lessonId: Long = 0L,
    val lessonSubject: String,
    val lessonTopic: String,
    val lessonColor: LessonColor,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val homework: String?
)