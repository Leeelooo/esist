package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.leeloo.esist.vo.Group

@Entity(tableName = "Groups", indices = [Index(value = ["group_name"], unique = true)])
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "group_name") var groupName: String = "",
    @ColumnInfo(name = "group_color") var groupColor: Int = 0
)

fun GroupEntity.toGroup(): Group = Group(
    groupId = this.groupId,
    groupName = this.groupName,
    groupColor = this.groupColor
)

fun Group.toEntityGroup(): GroupEntity = GroupEntity(
    groupId = this.groupId,
    groupName = this.groupName,
    groupColor = this.groupColor
)