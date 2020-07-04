package com.leeloo.esist.vo

data class Member(
    val memberId: Long = 0L,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val emailAddress: String,
    val memberColor: MemberColor
)
