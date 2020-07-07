package com.leeloo.esist.vo

data class MemberDetails(
    val memberId: Long = 0L,
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val memberGroups: List<Group>,
    val memberSchedule: List<Lesson>,
    val memberColor: MemberColor
)
