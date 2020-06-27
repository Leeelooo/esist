package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.GroupEntity
import com.leeloo.esist.cache.entities.LessonEntity
import com.leeloo.esist.cache.entities.LessonGroupCrossRef

data class RoomGroup(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "lesson_id",
        associateBy = Junction(LessonGroupCrossRef::class)
    )
    val lessons: List<LessonEntity>
)