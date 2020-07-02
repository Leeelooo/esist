package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.group.list.GroupAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LessonViewModel @Inject constructor(
    private val lessonUseCase: LessonUseCase
) : BaseViewModel<LessonViewState, LessonIntent, LessonAction>() {
    override val stateFlow: Flow<LessonViewState>
        get() = flowOf(LessonViewState.initial)

    override fun processAction(action: LessonAction) {
        when (action) {
            is LessonAction.LoadLessonsAction -> lessonUseCase.getFilteredLessons(action.phrase)
            is LessonAction.OpenDialogAction -> lessonUseCase.openDialog()
            is LessonAction.DismissDialogAction -> lessonUseCase.dismissDialog()
            is LessonAction.AddLessonAction -> lessonUseCase.addLesson(
                subjectName = action.subjectName,
                topicName = action.topicName,
                startTime = action.startTime,
                finishTime = action.finishTime,
                homework = action.homework
            )
        }
    }

}