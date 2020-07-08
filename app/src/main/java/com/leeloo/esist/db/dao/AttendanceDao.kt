package com.leeloo.esist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leeloo.esist.db.entity.AttendanceEntity
import com.leeloo.esist.db.entity.MemberEntity

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttendance(attendance: AttendanceEntity): Long

    @Query("DELETE FROM AttendanceEntity WHERE member_id = :memberId AND lesson_id = :lessonId")
    fun removeAttendance(memberId: Long, lessonId: Long)

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN AttendanceEntity ON AttendanceEntity.member_id = Members.member_id " +
                "WHERE AttendanceEntity.lesson_id = :lessonId " +
                "ORDER BY Members.first_name, Members.last_name ASC"
    )
    fun getVisitedMembers(lessonId: Long): List<MemberEntity>

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN AttendanceEntity ON AttendanceEntity.member_id = Members.member_id " +
                "WHERE AttendanceEntity.lesson_id IN (:lessonIds) " +
                "ORDER BY Members.first_name, Members.last_name ASC"
    )
    fun getVisitedMembers(lessonIds: List<Long>): List<MemberEntity>

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN AttendanceEntity ON AttendanceEntity.member_id = Members.member_id " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "WHERE Lessons.start_time > :dayStart AND Lessons.start_time < :dayFinish " +
                "ORDER BY Members.first_name, Members.last_name ASC"
    )
    fun getVisitedMembers(dayStart: Long, dayFinish: Long): List<MemberEntity>

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN AttendanceEntity ON AttendanceEntity.member_id = Members.member_id " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "WHERE Lessons.start_time > :dayStart AND Lessons.start_time < :dayFinish " +
                "ORDER BY Members.first_name, Members.last_name ASC"
    )
    fun getMembers(dayStart: Long, dayFinish: Long): List<MemberEntity>

}