package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Group

@Entity(tableName = "Groups", indices = [Index(value = ["group_name"], unique = true)])
data class GroupEntity(
    @PrimaryKey @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "group_name") val groupName: String,
    @ColumnInfo(name = "group_color") val groupColor: Int
)

fun GroupEntity.toGroup(): Group = Group(
    groupId = groupId,
    groupName = groupName,
    groupColor = groupColor
)