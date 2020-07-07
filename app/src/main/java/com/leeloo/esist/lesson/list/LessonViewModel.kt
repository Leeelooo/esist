package com.leeloo.esist.lesson.list

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LessonViewModel @ViewModelInject constructor(
    private val lessonRepository: LessonRepository
) : BaseViewModel<LessonViewState, LessonIntent, LessonAction>() {
    private var lastState: LessonViewState = LessonViewState.initial

    override val stateFlow: Flow<LessonViewState>
        get() = lessonRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: LessonAction) {
        when (action) {
            is LessonAction.LoadLessonsAction -> lessonRepository.getLessons()
            is LessonAction.OpenDialogAction -> lessonRepository.openDialog()
            is LessonAction.DismissDialogAction -> lessonRepository.dismissDialog()
            is LessonAction.AddLessonAction -> lessonRepository.addLesson(
                subjectName = action.subjectName,
                topicName = action.topicName,
                startTime = action.startTime,
                finishTime = action.finishTime,
                homework = action.homework,
                book = action.book,
                groups = lastState.selectedGroups
            )
            is LessonAction.GroupToAddAction -> lessonRepository.checkGroup(action.groupId)
        }
    }

}