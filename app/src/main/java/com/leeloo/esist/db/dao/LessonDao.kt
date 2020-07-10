package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.db.entity.LessonEntity

@Dao
interface LessonDao {
    @Query(
        "SELECT * FROM Lessons " +
                "ORDER BY start_time ASC"
    )
    suspend fun getLessons(): List<LessonEntity>

    @Query("SELECT * FROM Lessons WHERE lesson_id = :lessonId LIMIT 1")
    suspend fun getLessonDetails(lessonId: Long): LessonEntity?

    @Query(
        "SELECT DISTINCT * FROM Lessons as AllLessons " +
                "LEFT JOIN Lessons as GroupLessons ON AllLessons.lesson_id = GroupLessons.lesson_id " +
                "INNER JOIN LessonGroupCrossRef ON GroupLessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id = :groupId AND GroupLessons.lesson_id IS NULL " +
                "ORDER BY start_time ASC"
    )
    suspend fun getLessonsNotInGroup(groupId: Long): List<LessonEntity>

    @Query(
        "SELECT DISTINCT * FROM Lessons " +
                "INNER JOIN LessonGroupCrossRef ON Lessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id = :groupId " +
                "ORDER BY start_time ASC"
    )
    suspend fun getGroupLessons(groupId: Long): List<LessonEntity>

    @Query(
        "SELECT DISTINCT * FROM Lessons " +
                "INNER JOIN LessonGroupCrossRef ON Lessons.lesson_id = LessonGroupCrossRef.lesson_id " +
                "WHERE LessonGroupCrossRef.group_id IN (:groupIds) " +
                "ORDER BY start_time ASC"
    )
    suspend fun getGroupsLessons(groupIds: List<Long>): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Query(
        "SELECT COUNT(DISTINCT Members.member_id) FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "INNER JOIN Groups ON Groups.group_id = GroupMemberCrossRef.group_id " +
                "INNER JOIN LessonGroupCrossRef ON LessonGroupCrossRef.group_id = Groups.group_id " +
                "INNER JOIN Lessons ON LessonGroupCrossRef.lesson_id = Lessons.lesson_id " +
                "WHERE LessonGroupCrossRef.lesson_id = :lessonId AND Lessons.finish_time < :currentTimestamp"
    )
    suspend fun getTotalMemberCount(lessonId: Long, currentTimestamp: Long): Int

    @Query(
        "SELECT COUNT(AttendanceEntity.member_id) FROM AttendanceEntity " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "WHERE AttendanceEntity.lesson_id = :lessonId AND Lessons.finish_time < :currentTimestamp"
    )
    suspend fun getVisitedMemberCount(lessonId: Long, currentTimestamp: Long): Int


}