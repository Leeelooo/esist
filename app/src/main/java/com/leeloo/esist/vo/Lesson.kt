package com.leeloo.esist.vo

data class Lesson(
    val lessonId: Long,
    val lessonSubject: String,
    val lessonColor: LessonColor,
    val startTimestamp: Long,
    val endTimestamp: Long
)