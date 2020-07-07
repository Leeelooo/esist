package com.leeloo.esist.member

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.Member
import com.leeloo.esist.vo.MemberDetails

interface MemberLocalDataSource : BaseDataSource {
    suspend fun getAllMembers(): List<Member>
    suspend fun getMemberDetails(memberId: Long): MemberDetails?

    suspend fun getGroupMembersToAdd(groupId: Long): List<Member>

    suspend fun createMember(
        member: Member,
        selectedGroups: List<Long>
    ): Boolean
    suspend fun createMembers(members: List<Member>): Boolean
}

class MemberLocalDataSourceImpl(
    private val memberDao: MemberDao,
    private val groupDao: GroupDao,
    private val lessonDao: LessonDao,
    private val crossRefDao: CrossRefDao
) : MemberLocalDataSource {
    override suspend fun getAllMembers(): List<Member> =
        memberDao.getMembers().map { it.toMember() }

    override suspend fun getMemberDetails(memberId: Long): MemberDetails? {
        val member = memberDao.getMemberDetails(memberId) ?: return null
        val groups = groupDao.getMembersGroups(memberId)
        val lessons = lessonDao.getGroupsLessons(groups.map { it.groupId })
        return MemberDetails(
            memberId = member.memberId,
            emailAddress = member.emailAddress,
            memberColor = member.memberColor,
            lastName = member.lastName,
            firstName = member.firstName,
            memberGroups = groups.map { it.toGroup() },
            memberSchedule = lessons.map { it.toLesson() }
        )
    }

    override suspend fun getGroupMembersToAdd(groupId: Long): List<Member> =
        memberDao.getMembersNotInGroup(groupId).map { it.toMember() }

    override suspend fun createMember(
        member: Member,
        selectedGroups: List<Long>
    ): Boolean {
        val memberId: Long = memberDao.insertMember(member.toEntityMember())
        if (memberId == 0L) return false
        return selectedGroups.map {
            crossRefDao.insertGroupToMember(GroupMemberCrossRef(groupId = it, memberId = memberId))
        }.all { it != 0L }
    }

    override suspend fun createMembers(members: List<Member>): Boolean =
        memberDao.insertMembers(members.map { it.toEntityMember() }).all { it != 0L }

}