package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Relation
import com.leeloo.esist.cache.entities.LessonEntity
import com.leeloo.esist.cache.entities.MemberEntity

data class RoomLesson(
    @Embedded val lesson: LessonEntity,
    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "teacher_id"
    )
    val teacher: MemberEntity
)