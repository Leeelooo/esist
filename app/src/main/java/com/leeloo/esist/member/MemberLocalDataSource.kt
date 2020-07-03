package com.leeloo.esist.member

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.vo.Member
import com.leeloo.esist.vo.MemberDetails
import kotlinx.coroutines.flow.Flow

interface MemberLocalDataSource : BaseDataSource {
    fun getFilteredMembersFlow(phrase: String): Flow<List<Member>>
    fun getMemberDetailsFlow(memberId: Long): Flow<MemberDetails>

    suspend fun getGroupMembersToAdd(groupId: Long): List<Member>

    suspend fun createMember(member: Member): Boolean
    suspend fun createMembers(members: List<Member>): Boolean
}