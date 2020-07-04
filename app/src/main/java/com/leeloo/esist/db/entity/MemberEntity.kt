package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Member

@Entity(tableName = "Members")
data class MemberEntity(
    @PrimaryKey @ColumnInfo(name = "member_id") val memberId: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "middle_name") val middleName: String?,
    @ColumnInfo(name = "member_color") val memberColor: Int
)

fun MemberEntity.toMember(): Member = Member(
    memberId = memberId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    memberColor = memberColor
)