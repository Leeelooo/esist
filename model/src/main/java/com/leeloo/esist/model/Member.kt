package com.leeloo.esist.model

data class Member(
    val memberId: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val memberColor: MemberColor
)