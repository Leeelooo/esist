package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupMemberCrossRef(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)

@Entity
data class LessonGroupCrossRef(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L
)

@Entity
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)