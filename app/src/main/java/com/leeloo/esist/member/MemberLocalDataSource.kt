package com.leeloo.esist.member

import com.leeloo.esist.domain.base.BaseDataSource
import com.leeloo.esist.domain.vo.Result
import com.leeloo.esist.model.Member
import com.leeloo.esist.model.MemberDetails
import kotlinx.coroutines.flow.Flow

interface MemberLocalDataSource : BaseDataSource {
    fun getFilteredMembersFlow(phrase: String): Flow<List<Member>>
    fun getMemberDetailsFlow(memberId: Long): Flow<MemberDetails>

    suspend fun getGroupMembersToAdd(groupId: Long): List<Member>

    suspend fun createMember(member: Member): Boolean
    suspend fun createMembers(members: List<Member>): Boolean
}