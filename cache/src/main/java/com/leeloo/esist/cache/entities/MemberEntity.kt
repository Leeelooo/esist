package com.leeloo.esist.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Members")
data class MemberEntity(
    @PrimaryKey @ColumnInfo(name = "member_id") val memberId: Long,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "lest_name") val lastName: String,
    @ColumnInfo(name = "middle_name") val middleName: String?,
    @ColumnInfo(name = "member_color") val memberColor: Int
)