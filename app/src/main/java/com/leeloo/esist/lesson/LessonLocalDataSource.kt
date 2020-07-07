package com.leeloo.esist.lesson

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.AttendanceDao
import com.leeloo.esist.db.dao.CrossRefDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.entity.*
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
}

class LessonLocalDataSourceImpl(
    private val lessonDao: LessonDao,
    private val groupDao: GroupDao,
    private val attendanceDao: AttendanceDao,
    private val crossRefDao: CrossRefDao
) : LessonLocalDataSource {

    override suspend fun getLessons(): List<Lesson> =
        lessonDao.getLessons().map { it.toLesson() }

    override suspend fun getLessonDetails(lessonId: Long): LessonDetails? {
        val lesson = lessonDao.getLessonDetails(lessonId) ?: return null
        return LessonDetails(
            lessonId = lesson.lessonId,
            lessonColor = lesson.lessonColor,
            endTimestamp = lesson.finishTime,
            startTimestamp = lesson.startTime,
            lessonTopic = lesson.topicName,
            lessonSubject = lesson.subjectName,
            lessonHomework = lesson.homework,
            lessonGroups = groupDao.getLessonGroups(lessonId).map { it.toGroup() },
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
        attendanceDao.removeAttendance(
            AttendanceEntity(
                lessonId = lessonId,
                memberId = memberId
            )
        ) != 0

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

}