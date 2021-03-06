package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.lesson.LessonLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LessonDetailsRepository : BaseRepository<LessonDetailsModelState> {
    suspend fun loadLessonDetails(lessonId: Long)
    suspend fun setAttendance(lessonId: Long, memberId: Long)
    suspend fun removeAttendance(lessonId: Long, memberId: Long)
    fun initial()
    suspend fun getAttendance(lessonId: Long)
    fun dismissDialog()
}

@ExperimentalCoroutinesApi
class LessonDetailsRepositoryImpl(
    private val lessonLocalDataSource: LessonLocalDataSource
) : LessonDetailsRepository {
    private val modelStateFlow: MutableStateFlow<LessonDetailsModelState> =
        MutableStateFlow(LessonDetailsModelState.InitialLoading)

    override fun modelStateFlow(): Flow<LessonDetailsModelState> = modelStateFlow

    override suspend fun loadLessonDetails(lessonId: Long) {
        modelStateFlow.value = LessonDetailsModelState.InitialLoading
        val lessonDetails = lessonLocalDataSource.getLessonDetails(lessonId)
        modelStateFlow.value =
            if (lessonDetails != null) {
                LessonDetailsModelState.LessonDetailsLoaded(lessonDetails)
            } else {
                LessonDetailsModelState.LessonDetailsLoadingError(Exception("Error"))
            }
    }

    override suspend fun setAttendance(lessonId: Long, memberId: Long) {
        lessonLocalDataSource.addAttendance(lessonId, memberId)
    }

    override suspend fun removeAttendance(lessonId: Long, memberId: Long) {
        lessonLocalDataSource.removeAttendance(lessonId, memberId)
    }

    override fun initial() {
        modelStateFlow.value = LessonDetailsModelState.InitialLoading
    }

    override suspend fun getAttendance(lessonId: Long) {
        modelStateFlow.value = LessonDetailsModelState.LessonDetailsAttendance(
            lessonLocalDataSource.getAttendance(lessonId)
        )
    }

    override fun dismissDialog() {
        modelStateFlow.value = LessonDetailsModelState.DismissDialog
    }


}