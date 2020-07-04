package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.lesson.LessonLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface LessonDetailsRepository : BaseRepository<LessonDetailsModelState> {
    fun loadLessonDetails(lessonId: Long)
    suspend fun setAttendance(lessonId: Long, memberId: Long)
    suspend fun removeAttendance(lessonId: Long, memberId: Long)
}

@ExperimentalCoroutinesApi
class LessonDetailsRepositoryImpl @Inject constructor(
    private val lessonLocalDataSource: LessonLocalDataSource
) : LessonDetailsRepository {
    private val modelStateFlow: MutableStateFlow<LessonDetailsModelState> =
        MutableStateFlow(LessonDetailsModelState.InitialLoading)

    override fun modelStateFlow(): Flow<LessonDetailsModelState> = modelStateFlow

    override fun loadLessonDetails(lessonId: Long) {
        modelStateFlow.value = LessonDetailsModelState.InitialLoading
        modelStateFlow.combine(
            lessonLocalDataSource.getLessonDetails(
                lessonId
            )
        ) { _, lessonDetails ->
            if (lessonDetails != null) {
                LessonDetailsModelState.LessonDetailsLoaded(lessonDetails)
            } else {
                LessonDetailsModelState.LessonDetailsLoadingError(Exception("Error"))
            }
        }
    }

    override suspend fun setAttendance(lessonId: Long, memberId: Long) {
        lessonLocalDataSource.addAttendance(lessonId, memberId)
    }

    override suspend fun removeAttendance(lessonId: Long, memberId: Long) {
        lessonLocalDataSource.removeAttendance(lessonId, memberId)
    }


}