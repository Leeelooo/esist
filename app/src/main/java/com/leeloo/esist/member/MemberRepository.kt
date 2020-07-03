package com.leeloo.esist.member

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.vo.Member
import com.leeloo.esist.vo.MemberDetails
import com.leeloo.esist.vo.Result
import kotlinx.coroutines.flow.Flow

interface MemberRepository : BaseRepository {
    fun getFilteredMembersFlow(phrase: String): Flow<Result<List<Member>>>
    fun getMemberDetailsFlow(memberId: Long): Flow<Result<MemberDetails>>

    suspend fun getGroupMembersToAdd(groupId: Long): Result<List<Member>>

    suspend fun createMember(member: Member): Result<Boolean>
    suspend fun createMembers(members: List<Member>): Result<Boolean>
}