package com.leeloo.esist.model

data class MemberDetails(
    val memberId: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val memberGroups: List<Group>,
    val memberSchedule: List<Lesson>,
    val memberColor: MemberColor
)