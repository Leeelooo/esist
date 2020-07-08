package com.leeloo.esist

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leeloo.esist.db.EsistDatabase
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.GroupEntity
import com.leeloo.esist.db.entity.GroupMemberCrossRef
import com.leeloo.esist.db.entity.MemberEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var crossRefDao: CrossRefDao
    private lateinit var memberDao: MemberDao
    private lateinit var groupDao: GroupDao
    private lateinit var db: EsistDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, EsistDatabase::class.java
        ).build()
        crossRefDao = db.crossRefDao()
        memberDao = db.memberDao()
        groupDao = db.groupDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(SQLiteConstraintException::class)
    fun throwingExceptionOnUniqueConstraint() {
        runBlocking {
            val member: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            )
            val memberFake: MemberEntity = MemberEntity(
                firstName = "FakePaul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            )
            try {
                memberDao.insertMember(member)
                memberDao.insertMember(memberFake)
            } catch (e: SQLiteConstraintException) {
                assert(true)
            }
        }
    }

    @Test
    fun writeMemberAndAddToGroup() {
        runBlocking {
            val member: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            )
            val group = GroupEntity(
                groupName = "m3",
                groupColor = 1
            )
            val memberId = memberDao.insertMember(member)
            val groupId = groupDao.insertGroup(group)
            val crossRef = GroupMemberCrossRef(memberId = memberId, groupId = groupId)
            crossRefDao.insertGroupToMember(crossRef)
            val groups = groupDao.getMembersGroups(memberId)
            assert(groups[0].groupName == group.groupName)
            val members = memberDao.getGroupMembers(groupId)
            assert(members[0].emailAddress == member.emailAddress)
        }
    }

    @Test
    fun writeMultiplyEntities() {
        runBlocking {
            val member: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            )
            val member2: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "pau2l@gmail.com",
                memberColor = 1
            )
            val group = GroupEntity(
                groupName = "m3",
                groupColor = 1
            )
            val memberId = memberDao.insertMember(member)
            val member2Id = memberDao.insertMember(member2)
            val groupId = groupDao.insertGroup(group)
            val crossRef = GroupMemberCrossRef(memberId = memberId, groupId = groupId)
            val crossRef2 = GroupMemberCrossRef(memberId = member2Id, groupId = groupId)
            crossRefDao.insertGroupToMember(crossRef)
            crossRefDao.insertGroupToMember(crossRef2)
            val groups = groupDao.getMembersGroups(memberId)
            assert(groups[0].groupName == group.groupName)
            val members = memberDao.getGroupMembers(groupId)
            assert(members[0].emailAddress == member.emailAddress)
        }
    }

    @Test
    fun getGroupMember() {
        runBlocking {
            val member: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            )
            val member2: MemberEntity = MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "pau2l@gmail.com",
                memberColor = 1
            )
            val group = GroupEntity(
                groupName = "m3",
                groupColor = 1
            )
            val memberId = memberDao.insertMember(member)
            val member2Id = memberDao.insertMember(member2)
            val groupId = groupDao.insertGroup(group)
            val crossRef = GroupMemberCrossRef(memberId = memberId, groupId = groupId)
            crossRefDao.insertGroupToMember(crossRef)
            val members = memberDao.getGroupMembers(groupId)
            assert(members[0].memberId == memberId)
        }
    }

}