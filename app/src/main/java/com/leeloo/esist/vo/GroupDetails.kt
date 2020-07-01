package com.leeloo.esist.vo

data class GroupDetails(
    val groupId: Long,
    val groupName: String,
    val groupColor: GroupColor,
    val groupMembers: List<Member>,
    val groupSchedule: List<Lesson>
)