package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LessonViewModel @Inject constructor(
    private val lessonRepository: LessonRepository
) : BaseViewModel<LessonViewState, LessonIntent, LessonAction>() {
    override val stateFlow: Flow<LessonViewState>
        get() = flowOf(LessonViewState.initial)

    override suspend fun processAction(action: LessonAction) {
        when (action) {
            is LessonAction.LoadLessonsAction -> lessonRepository.getFilteredLessons(action.phrase)
            is LessonAction.OpenDialogAction -> lessonRepository.openDialog()
            is LessonAction.DismissDialogAction -> lessonRepository.dismissDialog()
            is LessonAction.AddLessonAction -> lessonRepository.addLesson(
                subjectName = action.subjectName,
                topicName = action.topicName,
                startTime = action.startTime,
                finishTime = action.finishTime,
                homework = action.homework
            )
        }
    }

}