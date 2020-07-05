package com.leeloo.esist.member

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.toEntityMember
import com.leeloo.esist.db.entity.toGroup
import com.leeloo.esist.db.entity.toLesson
import com.leeloo.esist.db.entity.toMember
import com.leeloo.esist.vo.Member
import com.leeloo.esist.vo.MemberDetails

interface MemberLocalDataSource : BaseDataSource {
    suspend fun getFilteredMembersFlow(phrase: String): List<Member>
    suspend fun getMemberDetails(memberId: Long): MemberDetails?

    suspend fun getGroupMembersToAdd(groupId: Long): List<Member>

    suspend fun createMember(member: Member): Boolean
    suspend fun createMembers(members: List<Member>): Boolean
}

class MemberLocalDataSourceImpl(
    private val memberDao: MemberDao,
    private val groupDao: GroupDao,
    private val lessonDao: LessonDao
) : MemberLocalDataSource {
    override suspend fun getFilteredMembersFlow(phrase: String): List<Member> =
        memberDao.getFilteredMember(phrase).map { it.toMember() }

    override suspend fun getMemberDetails(memberId: Long): MemberDetails? {
        val member = memberDao.getMemberDetails(memberId) ?: return null
        val groups = groupDao.getMembersGroups(memberId)
        val lessons = lessonDao.getGroupsLessons(groups.map { it.groupId })
        return MemberDetails(
            memberId = member.memberId,
            emailAddress = member.emailAddress,
            memberColor = member.memberColor,
            lastName = member.lastName,
            middleName = member.middleName,
            firstName = member.firstName,
            memberGroups = groups.map { it.toGroup() },
            memberSchedule = lessons.map { it.toLesson() }
        )
    }

    override suspend fun getGroupMembersToAdd(groupId: Long): List<Member> =
        memberDao.getMembersNotInGroup(groupId).map { it.toMember() }

    override suspend fun createMember(member: Member): Boolean =
        memberDao.insertMember(member.toEntityMember()) != 0L

    override suspend fun createMembers(members: List<Member>): Boolean =
        memberDao.insertMembers(members.map { it.toEntityMember() }).all { it != 0L }

}