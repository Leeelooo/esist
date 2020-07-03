package com.leeloo.esist.lesson

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.LessonDetails
import com.leeloo.esist.vo.Result
import kotlinx.coroutines.flow.Flow

interface LessonRepository : BaseRepository {
    fun getFilteredLessons(phrase: String): Flow<Result<List<Lesson>>>
    fun getLessonDetails(lessonId: Long): Flow<Result<LessonDetails>>

    suspend fun getGroupLessonsToAdd(groupId: Long): Result<List<Lesson>>

    suspend fun addAttendance(lessonId: Long, memberId: Long): Result<Boolean>
    suspend fun addAttendance(lessonId: Long, memberIds: List<Long>): Result<Boolean>

    suspend fun createLesson(lesson: Lesson): Result<Boolean>
    suspend fun createLessons(lessons: List<Lesson>): Result<Boolean>
}