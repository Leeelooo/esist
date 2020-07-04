package com.leeloo.esist.db.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.LessonDetails
import java.net.URI

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

fun RoomLessonDetails?.toLessonDetails(): LessonDetails? =
    if (this == null) null
    else LessonDetails(
        lessonId = this.lesson.lessonId,
        lessonSubject = this.lesson.subjectName,
        lessonTopic = this.lesson.topicName,
        lessonHomework = this.lesson.homework,
        lessonColor = this.lesson.lessonColor,
        lessonTeacher = this.teacher.toMember(),
        startTimestamp = this.lesson.startTime,
        endTimestamp = this.lesson.finishTime,
        lessonBooks = this.books.map { URI.create(it.bookUri) },
        lessonGroups = this.groups.map { it.group.toGroup() }
    )