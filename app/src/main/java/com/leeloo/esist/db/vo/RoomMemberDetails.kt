package com.leeloo.esist.db.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.MemberDetails

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
    val lessons: List<LessonEntity>
)

fun RoomMemberDetails?.toMemberDetails(): MemberDetails? =
    if (this == null) null
    else MemberDetails(
        memberId = this.member.memberId,
        firstName = this.member.firstName,
        middleName = this.member.middleName,
        lastName = this.member.lastName,
        emailAddress = this.member.emailAddress,
        memberGroups = this.groups.map { it.group.toGroup() },
        memberSchedule = this.groups.flatMap { it.lessons }.map { it.toLesson() },
        memberColor = this.member.memberColor
    )