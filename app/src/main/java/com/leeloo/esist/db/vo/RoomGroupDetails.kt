package com.leeloo.esist.db.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.GroupDetails

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

fun RoomGroupDetails.toGroupDetails(): GroupDetails = GroupDetails(
    groupId = this.group.groupId,
    groupName = this.group.groupName,
    groupColor = this.group.groupColor,
    groupMembers = this.members.map { it.toMember() },
    groupSchedule = this.lessons.map { it.toLesson() }
)