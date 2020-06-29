package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.*

data class RoomGroupDetails(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "member_id",
        associateBy = Junction(GroupMemberCrossRef::class)
    )
    val members: List<MemberEntity>,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "lesson_id",
        associateBy = Junction(LessonGroupCrossRef::class)
    )
    val lessons: List<RoomLesson>
)