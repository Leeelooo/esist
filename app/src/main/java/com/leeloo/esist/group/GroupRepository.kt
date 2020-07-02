package com.leeloo.esist.group

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.GroupDetails
import com.leeloo.esist.vo.Result
import kotlinx.coroutines.flow.Flow

interface GroupRepository : BaseRepository {
    fun getGroups(phrase: String): Flow<Result<List<Group>>>
    fun getGroupDetails(groupId: Long): Flow<Result<GroupDetails>>

    suspend fun getMemberGroupsToAdd(memberId: Long): List<Result<Group>>

    suspend fun addLessonToGroup(groupId: Long, lessonId: Long): Result<Boolean>
    suspend fun addLessonsToGroup(groupId: Long, lessonIds: List<Long>): Result<Boolean>
    suspend fun addMemberToGroup(groupId: Long, memberId: Long): Result<Boolean>
    suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Result<Boolean>

    suspend fun createGroup(group: Group): Result<Boolean>
    suspend fun createGroups(groups: List<Group>): Result<Boolean>

}