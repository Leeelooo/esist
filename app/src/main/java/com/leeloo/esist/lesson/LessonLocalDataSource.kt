package com.leeloo.esist.lesson

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.*
import com.leeloo.esist.db.entity.*
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.LessonDetails

interface LessonLocalDataSource : BaseDataSource {
    suspend fun getLessons(): List<Lesson>
    suspend fun getLessonDetails(lessonId: Long): LessonDetails?

    suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson>

    suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun removeAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun createLesson(
        lesson: Lesson,
        groups: List<Long>
    ): Boolean

    suspend fun getAttendance(lessonId: Long): Attendance
}

class LessonLocalDataSourceImpl(
    private val lessonDao: LessonDao,
    private val groupDao: GroupDao,
    private val attendanceDao: AttendanceDao,
    private val memberDao: MemberDao,
    private val crossRefDao: CrossRefDao
) : LessonLocalDataSource {

    override suspend fun getLessons(): List<Lesson> =
        lessonDao.getLessons().map { it.toLesson() }.toSet().toList()

    override suspend fun getLessonDetails(lessonId: Long): LessonDetails? {
        val lesson = lessonDao.getLessonDetails(lessonId) ?: return null
        val groups = groupDao.getLessonGroups(lessonId)
        return LessonDetails(
            lessonId = lesson.lessonId,
            lessonColor = lesson.lessonColor,
            endTimestamp = lesson.finishTime,
            startTimestamp = lesson.startTime,
            lessonTopic = lesson.topicName,
            lessonSubject = lesson.subjectName,
            lessonHomework = lesson.homework,
            lessonMembers = memberDao.getGroupsMembers(groups.map { it.groupId })
                .map { it.toMember() }.toSet().toList(),
            checkedMembers = attendanceDao.getVisitedMembers(lessonId).map { it.toMember() }.toSet()
                .toList(),
            lessonBook = lesson.book
        )
    }

    override suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson> =
        lessonDao.getLessonsNotInGroup(groupId).map { it.toLesson() }

    override suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean =
        attendanceDao.insertAttendance(
            AttendanceEntity(
                lessonId = lessonId,
                memberId = memberId
            )
        ) != 0L

    override suspend fun removeAttendance(lessonId: Long, memberId: Long): Boolean =
        true.also {
            attendanceDao.removeAttendance(
                lessonId = lessonId,
                memberId = memberId
            )
        }

    override suspend fun createLesson(
        lesson: Lesson,
        groups: List<Long>
    ): Boolean {
        val lessonId: Long = lessonDao.insertLesson(lesson.toLessonEntity())
        if (lessonId == 0L) return false
        return groups.map {
            crossRefDao.insertLessonToGroup(LessonGroupCrossRef(groupId = it, lessonId = lessonId))
        }.all { it != 0L }
    }

    override suspend fun getAttendance(lessonId: Long): Attendance {
        val lessonTotalMemberCount =
            lessonDao.getTotalMemberCount(lessonId, System.currentTimeMillis())
        val lessonVisitedMemberCount =
            lessonDao.getVisitedMemberCount(lessonId, System.currentTimeMillis())
        return Attendance(
            expectedAttendance = lessonTotalMemberCount,
            actualAttendance = lessonVisitedMemberCount
        )
    }

}