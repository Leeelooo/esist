package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lessons")
data class LessonEntity(
    @PrimaryKey @ColumnInfo(name = "lesson_id") val lessonId: Long,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "topic_name") val topicName: String,
    @ColumnInfo(name = "teacher_id") val teacherId: Long,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "finish_time") val finishTime: Long,
    @ColumnInfo(name = "homework") val homework: String?,
    @ColumnInfo(name = "lesson_color") val lessonColor: Int
)