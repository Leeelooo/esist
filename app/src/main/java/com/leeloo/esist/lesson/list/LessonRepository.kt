package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.vo.Lesson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LessonRepository : BaseRepository<LessonModelState> {
    suspend fun getFilteredLessons(phrase: String)

    fun openDialog()
    fun dismissDialog()

    suspend fun addLesson(
        subjectName: String,
        topicName: String,
        startTime: Long,
        finishTime: Long,
        homework: String?
    )
}

@ExperimentalCoroutinesApi
class LessonRepositoryImpl(
    private val lessonLocalDataSource: LessonLocalDataSource
) : LessonRepository {
    private val modelStateFlow: MutableStateFlow<LessonModelState> =
        MutableStateFlow(LessonModelState.Initial)

    override fun modelStateFlow(): Flow<LessonModelState> = modelStateFlow

    override suspend fun getFilteredLessons(phrase: String) {
        modelStateFlow.value = LessonModelState.Initial
        modelStateFlow.value =
            LessonModelState.LessonsLoaded(lessonLocalDataSource.getFilteredLessons(phrase))
    }

    override fun openDialog() {
        modelStateFlow.value = LessonModelState.DialogOpen
    }

    override fun dismissDialog() {
        modelStateFlow.value = LessonModelState.DialogDismiss
    }

    override suspend fun addLesson(
        subjectName: String,
        topicName: String,
        startTime: Long,
        finishTime: Long,
        homework: String?
    ) {
        val lesson = Lesson(
            lessonSubject = subjectName,
            lessonTopic = topicName,
            startTimestamp = startTime,
            endTimestamp = finishTime,
            homework = homework,
            lessonColor = 0
        ) //TODO: GENERATE LESSON COLOR
        if (lessonLocalDataSource.createLesson(lesson)) {
            modelStateFlow.value = LessonModelState.LessonInserted
        } else {
            modelStateFlow.value = LessonModelState.LessonInsetionError(Exception("Error"))
        }
    }
}