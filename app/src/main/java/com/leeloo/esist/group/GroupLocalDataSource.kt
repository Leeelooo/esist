package com.leeloo.esist.group

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.GroupDetails
import kotlinx.coroutines.flow.Flow

interface GroupLocalDataSource : BaseDataSource {
    fun getFilteredGroupsFlow(phrase: String): Flow<List<Group>>
    fun getGroupDetailsFlow(groupId: Long): Flow<GroupDetails>

    suspend fun getMemberGroupsToAdd(memberId: Long): List<Group>

    suspend fun addLessonToGroup(groupId: Long, lessonId: Long): Boolean
    suspend fun addLessonsToGroup(groupId: Long, lessonIds: List<Long>): Boolean
    suspend fun addMemberToGroup(groupId: Long, memberId: Long): Boolean
    suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Boolean

    suspend fun createGroup(group: Group): Boolean
    suspend fun createGroups(groups: List<Group>): Boolean
}