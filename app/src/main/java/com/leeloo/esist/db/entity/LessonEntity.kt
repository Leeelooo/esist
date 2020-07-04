package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Lesson

@Entity(tableName = "Lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "lesson_id") val lessonId: Long = 0L,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "topic_name") val topicName: String,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "finish_time") val finishTime: Long,
    @ColumnInfo(name = "homework") val homework: String?,
    @ColumnInfo(name = "lesson_color") val lessonColor: Int
)

fun LessonEntity.toLesson(): Lesson = Lesson(
    lessonId = this.lessonId,
    lessonSubject = this.subjectName,
    lessonColor = this.lessonColor,
    startTimestamp = this.startTime,
    endTimestamp = this.finishTime,
    homework = homework,
    lessonTopic = this.topicName
)

fun Lesson.toLessonEntity(): LessonEntity = LessonEntity(
    lessonId = this.lessonId,
    subjectName = this.lessonSubject,
    topicName = this.lessonTopic,
    lessonColor = this.lessonColor,
    startTime = this.startTimestamp,
    finishTime = this.endTimestamp,
    homework = this.homework
)