package com.leeloo.esist.member

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.toEntityMember
import com.leeloo.esist.db.entity.toMember
import com.leeloo.esist.db.vo.toMemberDetails
import com.leeloo.esist.vo.Member
import com.leeloo.esist.vo.MemberDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MemberLocalDataSource : BaseDataSource {
    fun getFilteredMembersFlow(phrase: String): Flow<List<Member>>
    fun getMemberDetailsFlow(memberId: Long): Flow<MemberDetails?>

    suspend fun getGroupMembersToAdd(groupId: Long): List<Member>

    suspend fun createMember(member: Member): Boolean
    suspend fun createMembers(members: List<Member>): Boolean
}

class MemberLocalDataSourceImpl @Inject constructor(
    private val memberDao: MemberDao
) : MemberLocalDataSource {
    override fun getFilteredMembersFlow(phrase: String): Flow<List<Member>> =
        memberDao.getFilteredMember(phrase)
            .distinctUntilChanged()
            .map { members -> members.map { it.toMember() } }

    override fun getMemberDetailsFlow(memberId: Long): Flow<MemberDetails?> =
        memberDao.getMemberDetails(memberId)
            .distinctUntilChanged()
            .map { it.toMemberDetails() }

    override suspend fun getGroupMembersToAdd(groupId: Long): List<Member> =
        memberDao.getMembersNotInGroup(groupId).map { it.toMember() }

    override suspend fun createMember(member: Member): Boolean =
        memberDao.insertMember(member.toEntityMember()) != 0L

    override suspend fun createMembers(members: List<Member>): Boolean =
        memberDao.insertMembers(members.map { it.toEntityMember() }).all { it != 0L }

}