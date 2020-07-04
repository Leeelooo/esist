package com.leeloo.esist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: EsistDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EsistDatabase::class.java,
                "esist_database.db"
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

    }

}