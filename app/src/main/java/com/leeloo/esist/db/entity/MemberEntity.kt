package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Member

@Entity(
    tableName = "Members",
    indices = [androidx.room.Index(value = ["email_address"], unique = true)]
)
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "member_id") var memberId: Long = 0L,
    @ColumnInfo(name = "first_name") var firstName: String = "",
    @ColumnInfo(name = "last_name") var lastName: String = "",
    @ColumnInfo(name = "email_address") var emailAddress: String = "",
    @ColumnInfo(name = "member_color") var memberColor: Int = 0
)

fun MemberEntity.toMember(): Member = Member(
    memberId = memberId,
    firstName = firstName,
    lastName = lastName,
    emailAddress = this.emailAddress,
    memberColor = memberColor
)

fun Member.toEntityMember(): MemberEntity = MemberEntity(
    memberId = memberId,
    firstName = firstName,
    lastName = lastName,
    emailAddress = this.emailAddress,
    memberColor = memberColor
)