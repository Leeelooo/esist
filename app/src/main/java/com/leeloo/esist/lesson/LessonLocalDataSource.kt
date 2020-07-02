package com.leeloo.esist.lesson

import com.leeloo.esist.domain.base.BaseDataSource
import com.leeloo.esist.model.Lesson
import com.leeloo.esist.model.LessonDetails
import kotlinx.coroutines.flow.Flow

interface LessonLocalDataSource : BaseDataSource {
    fun getFilteredLessons(phrase: String): Flow<List<Lesson>>
    fun getLessonDetails(lessonId: Long): Flow<LessonDetails>

    suspend fun getGroupLessonsToAdd(groupId: Long): List<Lesson>

    suspend fun addAttendance(lessonId: Long, memberId: Long): Boolean
    suspend fun addAttendance(lessonId: Long, memberIds: List<Long>): Boolean

    suspend fun createLesson(lesson: Lesson): Boolean
    suspend fun createLessons(lessons: List<Lesson>): Boolean
}