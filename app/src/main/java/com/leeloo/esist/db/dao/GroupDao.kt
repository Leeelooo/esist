package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.db.entity.GroupEntity

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(group: GroupEntity): Long

    @Query(
        "SELECT * from Groups " +
                "ORDER BY group_name ASC"
    )
    suspend fun getGroups(): List<GroupEntity>

    @Transaction
    @Query("SELECT * from Groups WHERE group_id = :groupId LIMIT 1")
    suspend fun getGroupDetails(groupId: Long): GroupEntity?

    @Query(
        "SELECT * from Groups " +
                "INNER JOIN LessonGroupCrossRef ON Groups.group_id = LessonGroupCrossRef.group_id " +
                "WHERE LessonGroupCrossRef.lesson_id = :lessonId " +
                "ORDER BY group_name ASC"
    )
    suspend fun getLessonGroups(lessonId: Long): List<GroupEntity>

    @Query(
        "SELECT * from Groups INNER JOIN GroupMemberCrossRef ON Groups.group_id = GroupMemberCrossRef.group_id WHERE member_id = :memberId ORDER BY group_name ASC"
    )
    suspend fun getMembersGroups(memberId: Long): List<GroupEntity>

    @Query(
        "SELECT COUNT(Members.member_id) FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "INNER JOIN Groups ON Groups.group_id = GroupMemberCrossRef.group_id " +
                "WHERE Groups.group_id = :groupId"
    )
    suspend fun getGroupMemberCount(groupId: Long): Int

    @Query(
        "SELECT COUNT(AttendanceEntity.member_id) FROM AttendanceEntity " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "INNER JOIN LessonGroupCrossRef ON LessonGroupCrossRef.lesson_id = Lessons.lesson_id " +
                "INNER JOIN Groups ON Groups.group_id = LessonGroupCrossRef.group_id " +
                "WHERE GROUPS.group_id = :groupId"
    )
    suspend fun getGroupAttendance(groupId: Long): Int

    @Query(
        "SELECT COUNT(LessonGroupCrossRef.lesson_id) FROM LessonGroupCrossRef " +
                "INNER JOIN Groups ON Groups.group_id = LessonGroupCrossRef.group_id " +
                "WHERE GROUPS.group_id = :groupId"
    )
    suspend fun getLessonsCount(groupId: Long): Int

}