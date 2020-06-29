package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.GroupEntity
import com.leeloo.esist.cache.entities.GroupMemberCrossRef
import com.leeloo.esist.cache.entities.LessonGroupCrossRef
import com.leeloo.esist.cache.entities.MemberEntity

data class RoomMemberDetails(
    @Embedded val member: MemberEntity,
    @Relation(
        parentColumn = "member_id",
        entityColumn = "group_id",
        associateBy = Junction(GroupMemberCrossRef::class)
    )
    val groups: List<GroupWithLessons>
)

data class GroupWithLessons(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "lesson_id",
        associateBy = Junction(LessonGroupCrossRef::class)
    )
    val lessons: List<RoomLesson>
)