package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.db.entity.MemberEntity
import com.leeloo.esist.db.vo.RoomMemberDetails
import com.leeloo.esist.vo.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Query(
        "SELECT * from Members " +
                "WHERE first_name || ' ' || middle_name || ' ' || last_name LIKE :phrase " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    fun getFilteredMember(phrase: String): Flow<List<MemberEntity>>

    @Transaction
    @Query("SELECT * from Members WHERE member_id = :memberId LIMIT 1")
    fun getMemberDetails(memberId: Long): Flow<RoomMemberDetails>

    @Query(
        "SELECT * from Members as AllMembers " +
                "LEFT JOIN Members as GroupMembers ON AllMembers.member_id = GroupMembers.member_id " +
                "INNER JOIN GroupMemberCrossRef as CrossRef ON GroupMembers.member_id = CrossRef.member_id" +
                "WHERE CrossRef.group_id = :groupId AND GroupMembers.member_id IS NULL " +
                "ORDER BY first_name, middle_name, last_name ASC"
    )
    suspend fun getMembersNotInGroup(groupId: Long): List<Member>

    @Insert
    suspend fun insertMember(member: MemberEntity): Long

    @Insert
    suspend fun insertMembers(members: List<MemberEntity>): List<Long>

    @Update
    suspend fun updateMember(member: MemberEntity): Long

}