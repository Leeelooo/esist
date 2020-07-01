package com.leeloo.esist.vo

data class Member(
    val memberId: Long,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val memberColor: MemberColor
)
