package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.*

data class RoomLessonDetails(
    @Embedded val lesson: LessonEntity,
    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "teacher_id"
    )
    val teacher: MemberEntity,
    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "book_id",
        associateBy = Junction(LessonBookCrossRef::class)
    )
    val books: List<BookEntity>,
    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "group_id",
        associateBy = Junction(LessonGroupCrossRef::class)
    )
    val groups: List<GroupWithMembers>
)

data class GroupWithMembers(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "member_id",
        associateBy = Junction(GroupMemberCrossRef::class)
    )
    val members: List<MemberEntity>
)