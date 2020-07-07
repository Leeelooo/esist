package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(indices = [androidx.room.Index(value = ["group_id", "member_id"], unique = true)])
data class GroupMemberCrossRef(
    @PrimaryKey @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)

@Entity(indices = [androidx.room.Index(value = ["lesson_id", "group_id"], unique = true)])
data class LessonGroupCrossRef(
    @PrimaryKey @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L
)

@Entity(indices = [androidx.room.Index(value = ["lesson_id", "member_id"], unique = true)])
data class AttendanceEntity(
    @PrimaryKey @ColumnInfo(name = "inner_id") var innerId: Long = 0L,
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)