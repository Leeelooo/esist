package com.leeloo.esist.lesson

import com.leeloo.esist.base.BaseDataSource
import com.leeloo.esist.db.dao.AttendanceDao
import com.leeloo.esist.db.dao.LessonDao
import com.leeloo.esist.db.entity.AttendanceEntity
import com.leeloo.esist.db.entity.toLesson
import com.leeloo.esist.db.entity.toLessonEntity
import com.leeloo.esist.db.vo.toLessonDetails
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.LessonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LessonLocalDataSource : BaseDataSource {
    fun getFilteredLessons(phrase: String): Flow<List<Lesson>>
    fun getLessonDetails(lessonId: Long): Flow<LessonDetails?>

    suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson>

    suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun removeAttendance(lessonId: Long, memberId: Long): Boolean

    suspend fun createLesson(lesson: Lesson): Boolean
    suspend fun createLessons(lessons: List<Lesson>): Boolean
}

class LessonLocalDataSourceImpl @Inject constructor(
    private val lessonDao: LessonDao,
    private val attendanceDao: AttendanceDao
) : LessonLocalDataSource {

    override fun getFilteredLessons(phrase: String): Flow<List<Lesson>> =
        lessonDao.getFilteredLesson(phrase)
            .distinctUntilChanged()
            .map { entity -> entity.map { it.toLesson() } }

    override fun getLessonDetails(lessonId: Long): Flow<LessonDetails?> =
        lessonDao.getLessonDetails(lessonId)
            .distinctUntilChanged()
            .map { it.toLessonDetails() }

    override suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson> =
        lessonDao.getLessonsNotInGroup(groupId).map { it.toLesson() }

    override suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean = attendanceDao
        .insertAttendance(AttendanceEntity(lessonId = lessonId, memberId = memberId)) != 0L

    override suspend fun removeAttendance(lessonId: Long, memberId: Long): Boolean = attendanceDao
        .removeAttendance(AttendanceEntity(lessonId = lessonId, memberId = memberId)) != 0L

    override suspend fun createLesson(lesson: Lesson): Boolean =
        lessonDao.insertLesson(lesson.toLessonEntity()) != 0L

    override suspend fun createLessons(lessons: List<Lesson>): Boolean =
        lessonDao.insertLessons(lessons.map { it.toLessonEntity() }).all { it != 0L }

}