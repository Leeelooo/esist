package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Lesson

@Entity(tableName = "Lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "subject_name") var subjectName: String = "",
    @ColumnInfo(name = "topic_name") var topicName: String = "",
    @ColumnInfo(name = "start_time") var startTime: Long = 0L,
    @ColumnInfo(name = "finish_time") var finishTime: Long = 0L,
    @ColumnInfo(name = "homework") var homework: String? = null,
    @ColumnInfo(name = "book") var book: String? = null,
    @ColumnInfo(name = "lesson_color") var lessonColor: Int = 0
)

fun LessonEntity.toLesson(): Lesson = Lesson(
    lessonId = this.lessonId,
    lessonSubject = this.subjectName,
    lessonColor = this.lessonColor,
    startTimestamp = this.startTime,
    endTimestamp = this.finishTime,
    homework = this.homework,
    book = this.book,
    lessonTopic = this.topicName
)

fun Lesson.toLessonEntity(): LessonEntity = LessonEntity(
    lessonId = this.lessonId,
    subjectName = this.lessonSubject,
    topicName = this.lessonTopic,
    lessonColor = this.lessonColor,
    startTime = this.startTimestamp,
    finishTime = this.endTimestamp,
    homework = this.homework,
    book = this.book
)