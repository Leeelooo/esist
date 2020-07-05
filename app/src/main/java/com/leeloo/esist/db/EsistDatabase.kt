package com.leeloo.esist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leeloo.esist.db.dao.*
import com.leeloo.esist.db.entity.*

@Database(
    entities = [
        BookEntity::class,
        GroupEntity::class,
        LessonEntity::class,
        MemberEntity::class,
        GroupMemberCrossRef::class,
        LessonGroupCrossRef::class,
        LessonBookCrossRef::class,
        AttendanceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EsistDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao
    abstract fun bookDao(): BookDao
    abstract fun crossRefDao(): CrossRefDao
    abstract fun groupDao(): GroupDao
    abstract fun lessonDao(): LessonDao
    abstract fun memberDao(): MemberDao
}