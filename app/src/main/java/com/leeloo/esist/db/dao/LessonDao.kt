package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.db.entity.LessonEntity
import com.leeloo.esist.db.vo.RoomLessonDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLessons(lessons: List<LessonEntity>): List<Long>

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Query(
        "SELECT * FROM Lessons " +
                "WHERE subject_name LIKE :phrase " +
                "ORDER BY start_time ASC"
    )
    fun getFilteredLesson(phrase: String): Flow<List<LessonEntity>>

    @Transaction
    @Query("SELECT * FROM Lessons WHERE lesson_id = :lessonId LIMIT 1")
    fun getLessonDetails(lessonId: Long): Flow<RoomLessonDetails?>

    @Query(
        "SELECT * FROM Lessons as AllLessons " +
                "LEFT JOIN Lessons as GroupLessons ON AllLessons.lesson_id = GroupLessons.lesson_id " +
                "INNER JOIN LessonGroupCrossRef ON GroupLessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id = :groupId AND GroupLessons.lesson_id IS NULL"
    )
    suspend fun getLessonsNotInGroup(groupId: Long): List<LessonEntity>

}