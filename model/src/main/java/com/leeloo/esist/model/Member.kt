package com.leeloo.esist.model

data class Member(
    val memberId: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val memberGroups: List<Group>,
    val memberColor: MemberColor
)