package com.leeloo.esist.db.vo

import androidx.room.Embedded
import androidx.room.Relation
import com.leeloo.esist.db.entity.LessonEntity
import com.leeloo.esist.db.entity.MemberEntity
import com.leeloo.esist.db.entity.toMember
import com.leeloo.esist.vo.Lesson

data class RoomLesson(
    @Embedded val lesson: LessonEntity,
    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "teacher_id"
    )
    val teacher: MemberEntity
)

fun RoomLesson.toLesson(): Lesson = Lesson(
    lessonId = this.lesson.lessonId,
    lessonSubject = this.lesson.subjectName,
    lessonColor = this.lesson.lessonColor,
    lessonTeacher = this.teacher.toMember(),
    startTimestamp = this.lesson.startTime,
    endTimestamp = this.lesson.finishTime
)