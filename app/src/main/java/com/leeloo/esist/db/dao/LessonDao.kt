package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.db.entity.LessonEntity

@Dao
interface LessonDao {
    @Query(
        "SELECT * FROM Lessons " +
                "WHERE subject_name LIKE :phrase " +
                "ORDER BY start_time ASC"
    )
    suspend fun getFilteredLesson(phrase: String): List<LessonEntity>

    @Query("SELECT * FROM Lessons WHERE lesson_id = :lessonId LIMIT 1")
    suspend fun getLessonDetails(lessonId: Long): LessonEntity?

    @Query(
        "SELECT * FROM Lessons as AllLessons " +
                "LEFT JOIN Lessons as GroupLessons ON AllLessons.lesson_id = GroupLessons.lesson_id " +
                "INNER JOIN LessonGroupCrossRef ON GroupLessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id = :groupId AND GroupLessons.lesson_id IS NULL " +
                "ORDER BY start_time ASC"
    )
    suspend fun getLessonsNotInGroup(groupId: Long): List<LessonEntity>

    @Query(
        "SELECT * FROM Lessons " +
                "INNER JOIN LessonGroupCrossRef ON Lessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id = :groupId " +
                "ORDER BY start_time ASC"
    )
    suspend fun getGroupLessons(groupId: Long): List<LessonEntity>

    @Query(
        "SELECT * FROM Lessons " +
                "INNER JOIN LessonGroupCrossRef ON Lessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id IN (:groupIds) " +
                "ORDER BY start_time ASC"
    )
    suspend fun getGroupsLessons(groupIds: List<Long>): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLessons(lessons: List<LessonEntity>): List<Long>

    @Update
    suspend fun updateLesson(lesson: LessonEntity)


}