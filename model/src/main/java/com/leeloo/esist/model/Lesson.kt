package com.leeloo.esist.model

data class Lesson(
    val lessonId: Long,
    val lessonSubject: String,
    val lessonColor: LessonColor,
    val lessonTeacher: Member,
    val startTimestamp: Long,
    val endTimestamp: Long
)