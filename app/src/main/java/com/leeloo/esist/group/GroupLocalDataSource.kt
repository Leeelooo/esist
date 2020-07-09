package com.leeloo.esist.group

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.GroupDetails

interface GroupLocalDataSource : BaseDataSource {
    suspend fun getGroups(): List<Group>
    suspend fun getGroupDetails(groupId: Long): GroupDetails?

    suspend fun getMemberGroupsToAdd(memberId: Long): List<Group>

    suspend fun addLessonToGroup(groupId: Long, lessonId: Long): Boolean
    suspend fun addLessonsToGroup(groupId: Long, lessonIds: List<Long>): Boolean
    suspend fun addMemberToGroup(groupId: Long, memberId: Long): Boolean
    suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Boolean

    suspend fun createGroup(
        group: Group,
        selectedLessons: List<Long>,
        selectedMembers: List<Long>
    ): Boolean

    suspend fun getAttendance(groupId: Long): Attendance
}

class GroupLocalDataSourceImpl(
    private val groupDao: GroupDao,
    private val lessonDao: LessonDao,
    private val memberDao: MemberDao,
    private val crossRefDao: CrossRefDao
) : GroupLocalDataSource {

    override suspend fun getGroups(): List<Group> =
        groupDao.getGroups().map { it.toGroup() }

    override suspend fun getGroupDetails(groupId: Long): GroupDetails? {
        val group = groupDao.getGroupDetails(groupId) ?: return null
        return GroupDetails(
            groupId = group.groupId,
            groupName = group.groupName,
            groupColor = group.groupColor,
            groupSchedule = lessonDao.getGroupLessons(groupId).map { it.toLesson() },
            groupMembers = memberDao.getGroupMembers(groupId).map { it.toMember() }
        )
    }

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

    override suspend fun addMemberToGroup(groupId: Long, memberId: Long): Boolean =
        crossRefDao.insertGroupToMember(
            GroupMemberCrossRef(
                groupId = groupId,
                memberId = memberId
            )
        ) != 0L

    override suspend fun addMembersToGroup(groupId: Long, memberIds: List<Long>): Boolean =
        memberIds.map {
            crossRefDao.insertGroupToMember(
                GroupMemberCrossRef(groupId = groupId, memberId = it)
            )
        }.all { it != 0L }

    override suspend fun createGroup(
        group: Group,
        selectedLessons: List<Long>,
        selectedMembers: List<Long>
    ): Boolean {
        val groupId = groupDao.insertGroup(group.toEntityGroup())
        if (groupId == 0L) return false
        val addedLessons = selectedLessons.map {
            crossRefDao.insertLessonToGroup(LessonGroupCrossRef(groupId = groupId, lessonId = it))
        }.all { it != 0L }
        val addedMembers = selectedMembers.map {
            crossRefDao.insertGroupToMember(GroupMemberCrossRef(groupId = groupId, memberId = it))
        }.all { it != 0L }
        return addedLessons && addedMembers
    }

    override suspend fun getAttendance(groupId: Long): Attendance {
        val memberCount = groupDao.getGroupMemberCount(groupId) * groupDao.getLessonsCount(groupId)
        val attendanceCount = groupDao.getGroupAttendance(groupId)
        return Attendance(expectedAttendance = memberCount, actualAttendance = attendanceCount)
    }

}