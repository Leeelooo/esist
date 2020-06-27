package com.leeloo.esist.cache.entities

import androidx.room.Entity

@Entity(primaryKeys = ["group_id", "member_id"])
data class GroupMemberCrossRef(
    val groupId: Long,
    val memberId: Long
)

@Entity(primaryKeys = ["lesson_id", "group_id"])
data class LessonGroupCrossRef(
    val lessonId: Long,
    val groupId: Long
)

@Entity(primaryKeys = ["lesson_id", "book_id"])
data class LessonBookCrossRef(
    val lessonId: Long,
    val bookId: Long
)

@Entity(primaryKeys = ["lesson_id", "member_id"])
data class AttendanceEntity(
    val lessonId: Long,
    val memberId: Long
)