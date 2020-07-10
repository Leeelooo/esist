package com.leeloo.esist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.leeloo.esist.db.entity.MemberEntity

@Dao
interface MemberDao {

    @Query(
        "SELECT * from Members " +
                "ORDER BY first_name, last_name ASC"
    )
    suspend fun getMembers(): List<MemberEntity>

    @Transaction
    @Query("SELECT * from Members WHERE member_id = :memberId LIMIT 1")
    suspend fun getMemberDetails(memberId: Long): MemberEntity?

    @Query(
        "SELECT DISTINCT * FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "WHERE GroupMemberCrossRef.group_id = :groupId " +
                "ORDER BY first_name, last_name ASC"
    )
    suspend fun getGroupMembers(groupId: Long): List<MemberEntity>

    @Query(
        "SELECT DISTINCT * FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "WHERE GroupMemberCrossRef.group_id IN (:groupIds) " +
                "ORDER BY first_name, last_name ASC"
    )
    suspend fun getGroupsMembers(groupIds: List<Long>): List<MemberEntity>

    @Query(
        "SELECT DISTINCT * from Members as AllMembers " +
                "LEFT JOIN Members as GroupMembers ON AllMembers.member_id = GroupMembers.member_id " +
                "INNER JOIN GroupMemberCrossRef ON GroupMembers.member_id = GroupMemberCrossRef.member_id " +
                "WHERE GroupMemberCrossRef.group_id = :groupId AND GroupMembers.member_id IS NULL " +
                "ORDER BY first_name, last_name ASC"
    )
    suspend fun getMembersNotInGroup(groupId: Long): List<MemberEntity>

    @Insert
    suspend fun insertMember(member: MemberEntity): Long

    @Insert
    suspend fun insertMembers(members: List<MemberEntity>): List<Long>

    @Query(
        "SELECT COUNT(DISTINCT AttendanceEntity.lesson_id) FROM AttendanceEntity " +
                "INNER JOIN Members ON Members.member_id = AttendanceEntity.member_id " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "WHERE Members.member_id = :memberId AND Lessons.finish_time < :currentTimeMillis"
    )
    suspend fun getMemberVisitedLessonCount(memberId: Long, currentTimeMillis: Long): Int

    @Query(
        "SELECT COUNT(DISTINCT AttendanceEntity.lesson_id) FROM AttendanceEntity " +
                "INNER JOIN Lessons ON Lessons.lesson_id = AttendanceEntity.lesson_id " +
                "INNER JOIN LessonGroupCrossRef ON LessonGroupCrossRef.lesson_id = Lessons.lesson_id " +
                "INNER JOIN Groups ON Groups.group_id = LessonGroupCrossRef.group_id " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.group_id = Groups.group_id " +
                "WHERE GroupMemberCrossRef.member_id = :memberId AND Lessons.finish_time < :currentTimeMillis"
    )
    suspend fun getMemberLessonCount(memberId: Long, currentTimeMillis: Long): Int


}