package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Member

@Entity(tableName = "Members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "member_id") val memberId: Long = 0L,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "middle_name") val middleName: String?,
    @ColumnInfo(name = "email_address") val emailAddress: String,
    @ColumnInfo(name = "member_color") val memberColor: Int
)

fun MemberEntity.toMember(): Member = Member(
    memberId = memberId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    emailAddress = this.emailAddress,
    memberColor = memberColor
)

fun Member.toEntityMember(): MemberEntity = MemberEntity(
    memberId = memberId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    emailAddress = this.emailAddress,
    memberColor = memberColor
)