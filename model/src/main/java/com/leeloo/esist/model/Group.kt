package com.leeloo.esist.model

data class Group(
    val groupId: Long,
    val groupName: String,
    val groupColor: GroupColor,
    val groupMembers: List<Member>,
    val groupSchedule: List<Lesson>
)