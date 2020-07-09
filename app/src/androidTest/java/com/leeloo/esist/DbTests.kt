package com.leeloo.esist

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leeloo.esist.db.EsistDatabase
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.dao.MemberDao
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.group.GroupLocalDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var crossRefDao: CrossRefDao
    private lateinit var lessonDao: LessonDao
    private lateinit var memberDao: MemberDao
    private lateinit var groupDao: GroupDao
    private lateinit var groupLocalDataSource: GroupLocalDataSource
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
        lessonDao = db.lessonDao()
        groupLocalDataSource = GroupLocalDataSourceImpl(groupDao, lessonDao, memberDao, crossRefDao)
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

    @Test
    fun lessonCorrectness() {
        runBlocking {
            val lesson = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            val lesson2 = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            val lesson3 = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            lessonDao.insertLesson(lesson)
            lessonDao.insertLesson(lesson2)
            lessonDao.insertLesson(lesson3)
            assert(lessonDao.getLessons().size == 3)
        }
    }

    @Test
    fun groupLessons() {
        runBlocking {
            val lesson = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            val lesson2 = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            val lesson3 = LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
            val group = GroupEntity(
                groupName = "m3",
                groupColor = 1
            )
            val lessonId = lessonDao.insertLesson(lesson)
            val lesson2Id = lessonDao.insertLesson(lesson2)
            val lesson3Id = lessonDao.insertLesson(lesson3)
            lesson.lessonId = lessonId
            lesson2.lessonId = lesson2Id
            lesson3.lessonId = lesson3Id
            val groupId = groupDao.insertGroup(group)
            crossRefDao.insertLessonToGroup(
                LessonGroupCrossRef(
                    groupId = groupId,
                    lessonId = lessonId
                )
            )
            crossRefDao.insertLessonToGroup(
                LessonGroupCrossRef(
                    groupId = groupId,
                    lessonId = lesson3Id
                )
            )
            assert(!lessonDao.getLessons().contains(lesson2))
        }
    }

    @Test
    fun mappingGroupTest() {
        runBlocking {
            val group = GroupEntity(
                groupName = "m323",
                groupColor = 1
            )
            val group1 = GroupEntity(
                groupName = "m3",
                groupColor = 2
            )
            group.groupId = groupDao.insertGroup(group)
            group1.groupId = groupDao.insertGroup(group1)
            val mappedGroups = groupLocalDataSource.getGroups()
            assert(mappedGroups[0] == group.toGroup() && mappedGroups[1] == group1.toGroup())
        }
    }

    @Test
    fun groupDetailsCorrectness() {
        val group = GroupEntity(
            groupName = "m323",
            groupColor = 1
        )
        val members = listOf(
            MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "paul@gmail.com",
                memberColor = 1
            ),
            MemberEntity(
                firstName = "Paul",
                lastName = "Chlonde",
                emailAddress = "pau2l@gmail.com",
                memberColor = 1
            )
        )
        val lessons = listOf(
            LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            ),
            LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            ),
            LessonEntity(
                lessonColor = 1,
                homework = null,
                book = null,
                finishTime = 2L,
                startTime = 1L,
                topicName = "klds",
                subjectName = "fdsm;"
            )
        )
        runBlocking {
            group.groupId = groupDao.insertGroup(group)
            members.forEach {
                it.memberId = memberDao.insertMember(it)
                crossRefDao.insertGroupToMember(
                    GroupMemberCrossRef(
                        memberId = it.memberId,
                        groupId = group.groupId
                    )
                )
            }
            lessons.forEach {
                it.lessonId = lessonDao.insertLesson(it)
                crossRefDao.insertLessonToGroup(
                    LessonGroupCrossRef(
                        lessonId = it.lessonId,
                        groupId = group.groupId
                    )
                )
            }
            val groupDetails = groupLocalDataSource.getGroupDetails(group.groupId)
            if (groupDetails == null) assert(false)
            assert(
                groupDetails!!.groupName == group.groupName
                        && groupDetails.groupId == group.groupId
                        && groupDetails.groupColor == group.groupColor
            )
            members.forEach {
                assert(groupDetails.groupMembers.contains(it.toMember()))
            }
            lessons.forEach {
                assert(groupDetails.groupSchedule.contains(it.toLesson()))
            }
        }
    }

}