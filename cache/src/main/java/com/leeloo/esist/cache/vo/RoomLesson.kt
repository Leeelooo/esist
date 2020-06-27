package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.BookEntity
import com.leeloo.esist.cache.entities.LessonBookCrossRef
import com.leeloo.esist.cache.entities.LessonEntity
import com.leeloo.esist.cache.entities.MemberEntity

data class RoomLesson(
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
    val books: List<BookEntity>
)