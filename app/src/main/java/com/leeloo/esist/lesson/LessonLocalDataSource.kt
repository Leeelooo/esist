package com.leeloo.esist.lesson

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.AttendanceDao
import com.leeloo.esist.db.dao.BookDao
import com.leeloo.esist.db.dao.GroupDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.entity.AttendanceEntity
import com.leeloo.esist.db.entity.toGroup
import com.leeloo.esist.db.entity.toLesson
import com.leeloo.esist.db.entity.toLessonEntity
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.LessonDetails
import java.net.URI

interface LessonLocalDataSource : BaseDataSource {
    suspend fun getFilteredLessons(phrase: String): List<Lesson>
    suspend fun getLessonDetails(lessonId: Long): LessonDetails?

    suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson>

    suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun removeAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun createLesson(lesson: Lesson): Boolean
}

class LessonLocalDataSourceImpl(
    private val lessonDao: LessonDao,
    private val bookDao: BookDao,
    private val groupDao: GroupDao,
    private val attendanceDao: AttendanceDao
) : LessonLocalDataSource {

    override suspend fun getFilteredLessons(phrase: String): List<Lesson> =
        lessonDao.getFilteredLesson(phrase).map { it.toLesson() }

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
            lessonBooks = bookDao.getLessonBooks(lessonId).map { URI.create(it.bookUri) }
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

    override suspend fun createLesson(lesson: Lesson): Boolean =
        lessonDao.insertLesson(lesson.toLessonEntity()) != 0L

}