package com.leeloo.esist.group

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.entity.GroupMemberCrossRef
import com.leeloo.esist.db.entity.LessonGroupCrossRef
import com.leeloo.esist.db.entity.toGroup
import com.leeloo.esist.db.vo.toGroupDetails
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.GroupDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GroupLocalDataSource : BaseDataSource {
    fun getFilteredGroupsFlow(phrase: String): Flow<List<Group>>
    fun getGroupDetailsFlow(groupId: Long): Flow<GroupDetails?>

    suspend fun getMemberGroupsToAdd(memberId: Long): List<Group>

    suspend fun addLessonToGroup(groupId: Long, lessonId: Long): Boolean
    suspend fun addLessonsToGroup(groupId: Long, lessonIds: List<Long>): Boolean
    suspend fun addMemberToGroup(groupId: Long, memberId: Long): Boolean
    suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Boolean

    suspend fun createGroup(group: Group): Boolean
    suspend fun createGroups(groups: List<Group>): Boolean
}

class GroupLocalDataSourceImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val crossRefDao: CrossRefDao
) : GroupLocalDataSource {

    override fun getFilteredGroupsFlow(phrase: String): Flow<List<Group>> =
        groupDao.getFilteredGroups(phrase)
            .distinctUntilChanged()
            .map { groups -> groups.map { it.toGroup() } }

    override fun getGroupDetailsFlow(groupId: Long): Flow<GroupDetails?> =
        groupDao.getGroupDetails(groupId)
            .distinctUntilChanged()
            .map { it.toGroupDetails() }

    override suspend fun getMemberGroupsToAdd(memberId: Long): List<Group> =
        throw UnsupportedOperationException("Don't need yet")

    override suspend fun addLessonToGroup(groupId: Long, lessonId: Long): Boolean = crossRefDao
        .insertLessonToGroup(LessonGroupCrossRef(groupId = groupId, lessonId = lessonId)) != 0L

    override suspend fun addLessonsToGroup(groupId: Long, lessonIds: List<Long>): Boolean =
        lessonIds.map {
            crossRefDao.insertLessonToGroup(
                LessonGroupCrossRef(groupId = groupId, lessonId = it)
            )
        }.all { it != 0L }

    override suspend fun addMemberToGroup(groupId: Long, memberId: Long): Boolean = crossRefDao
        .insertGroupToMember(GroupMemberCrossRef(groupId = groupId, memberId = memberId)) != 0L

    override suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Boolean =
        memberIds.map {
            crossRefDao.insertGroupToMember(
                GroupMemberCrossRef(groupId = groupId, memberId = it)
            )
        }.all { it != 0L }

    override suspend fun createGroup(group: Group): Boolean = groupDao.insertGroup(group) != 0L

    override suspend fun createGroups(groups: List<Group>): Boolean = groupDao
        .insertGroups(groups)
        .all { it != 0L }

}