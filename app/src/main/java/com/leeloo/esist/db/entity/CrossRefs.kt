package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["group_id", "member_id"])
data class GroupMemberCrossRef(
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "member_id") val memberId: Long
)

@Entity(primaryKeys = ["lesson_id", "group_id"])
data class LessonGroupCrossRef(
    @ColumnInfo(name = "lesson_id") val lessonId: Long,
    @ColumnInfo(name = "group_id") val groupId: Long
)

@Entity(primaryKeys = ["lesson_id", "book_id"])
data class LessonBookCrossRef(
    @ColumnInfo(name = "lesson_id") val lessonId: Long,
    @ColumnInfo(name = "book_id") val bookId: Long
)

@Entity(primaryKeys = ["lesson_id", "member_id"])
data class AttendanceEntity(
    @ColumnInfo(name = "lesson_id") val lessonId: Long,
    @ColumnInfo(name = "member_id") val memberId: Long
)