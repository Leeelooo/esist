package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.vo.Lesson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface LessonRepository : BaseRepository<LessonModelState> {
    fun getFilteredLessons(phrase: String)

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
class LessonRepositoryImpl @Inject constructor(
    private val lessonLocalDataSource: LessonLocalDataSource
) : LessonRepository {
    private val modelStateFlow: MutableStateFlow<LessonModelState> =
        MutableStateFlow(LessonModelState.Initial)

    override fun modelStateFlow(): Flow<LessonModelState> = modelStateFlow

    override fun getFilteredLessons(phrase: String) {
        modelStateFlow.value = LessonModelState.Initial
        modelStateFlow.combine(
            lessonLocalDataSource
                .getFilteredLessons(phrase)
        ) { _, lessons ->
            LessonModelState.LessonsLoaded(lessons)
        }
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