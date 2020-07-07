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
        "SELECT * from Groups " +
                "INNER JOIN GroupMemberCrossRef ON Groups.group_id = GroupMemberCrossRef.group_id " +
                "WHERE GroupMemberCrossRef.member_id = :memberId " +
                "ORDER BY group_name ASC"
    )
    suspend fun getMembersGroups(memberId: Long): List<GroupEntity>

}