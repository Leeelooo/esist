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
                "WHERE first_name || ' ' || middle_name || ' ' || last_name LIKE :phrase " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    suspend fun getFilteredMember(phrase: String): List<MemberEntity>

    @Transaction
    @Query("SELECT * from Members WHERE member_id = :memberId LIMIT 1")
    suspend fun getMemberDetails(memberId: Long): MemberEntity?

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "WHERE GroupMemberCrossRef.group_id = :groupId " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    suspend fun getGroupMembers(groupId: Long): List<MemberEntity>

    @Query(
        "SELECT * FROM Members " +
                "INNER JOIN GroupMemberCrossRef ON GroupMemberCrossRef.member_id = Members.member_id " +
                "WHERE GroupMemberCrossRef.group_id IN (:groupIds) " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    suspend fun getGroupsMembers(groupIds: List<Long>): List<MemberEntity>

    @Query(
        "SELECT * from Members as AllMembers " +
                "LEFT JOIN Members as GroupMembers ON AllMembers.member_id = GroupMembers.member_id " +
                "INNER JOIN GroupMemberCrossRef ON GroupMembers.member_id = GroupMemberCrossRef.member_id " +
                "WHERE GroupMemberCrossRef.group_id = :groupId AND GroupMembers.member_id IS NULL " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    suspend fun getMembersNotInGroup(groupId: Long): List<MemberEntity>

    @Insert
    suspend fun insertMember(member: MemberEntity): Long

    @Insert
    suspend fun insertMembers(members: List<MemberEntity>): List<Long>

}