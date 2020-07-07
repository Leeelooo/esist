package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.utils.getRandomColor
import com.leeloo.esist.vo.Lesson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LessonRepository : BaseRepository<LessonModelState> {
    suspend fun getLessons()

    suspend fun openDialog()
    fun dismissDialog()
    fun checkGroup(groupId: Long)

    suspend fun addLesson(
        subjectName: String,
        topicName: String,
        startTime: Long,
        finishTime: Long,
        homework: String?,
        book: String?,
        groups: List<Long>
    )
}

@ExperimentalCoroutinesApi
class LessonRepositoryImpl(
    private val lessonLocalDataSource: LessonLocalDataSource,
    private val groupLocalDataSource: GroupLocalDataSource
) : LessonRepository {
    private val modelStateFlow: MutableStateFlow<LessonModelState> =
        MutableStateFlow(LessonModelState.Initial)

    override fun modelStateFlow(): Flow<LessonModelState> = modelStateFlow

    override suspend fun getLessons() {
        modelStateFlow.value = LessonModelState.Initial
        modelStateFlow.value =
            LessonModelState.LessonsLoaded(lessonLocalDataSource.getLessons())
    }

    override suspend fun openDialog() {
        modelStateFlow.value = LessonModelState.DialogOpen(groupLocalDataSource.getGroups())
    }

    override fun dismissDialog() {
        modelStateFlow.value = LessonModelState.DialogDismiss
    }

    override fun checkGroup(groupId: Long) {
        modelStateFlow.value = LessonModelState.GroupSelected(groupId)
    }

    override suspend fun addLesson(
        subjectName: String,
        topicName: String,
        startTime: Long,
        finishTime: Long,
        homework: String?,
        book: String?,
        groups: List<Long>
    ) {
        val lesson = Lesson(
            lessonSubject = subjectName,
            lessonTopic = topicName,
            startTimestamp = startTime,
            endTimestamp = finishTime,
            homework = homework,
            book = book,
            lessonColor = getRandomColor()
        )
        try {
            if (lessonLocalDataSource.createLesson(lesson, groups)) {
                modelStateFlow.value = LessonModelState.LessonInserted
                modelStateFlow.value =
                    LessonModelState.LessonsLoaded(lessonLocalDataSource.getLessons())
            } else {
                modelStateFlow.value = LessonModelState.LessonInsetionError(Exception("Error"))
            }
        } catch (e: Exception) {
            modelStateFlow.value = LessonModelState.LessonInsetionError(e)
        }
    }
}