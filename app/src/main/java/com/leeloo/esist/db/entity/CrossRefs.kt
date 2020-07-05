package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["group_id", "member_id"])
data class GroupMemberCrossRef(
    @ColumnInfo(name = "group_id") var groupId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)

@Entity(primaryKeys = ["lesson_id", "group_id"])
data class LessonGroupCrossRef(
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "group_id") var groupId: Long = 0L
)

@Entity(primaryKeys = ["lesson_id", "book_id"])
data class LessonBookCrossRef(
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "book_id") var bookId: Long = 0L
)

@Entity(primaryKeys = ["lesson_id", "member_id"])
data class AttendanceEntity(
    @ColumnInfo(name = "lesson_id") var lessonId: Long = 0L,
    @ColumnInfo(name = "member_id") var memberId: Long = 0L
)