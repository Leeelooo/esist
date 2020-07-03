package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LessonDetailsViewModel @Inject constructor(
    private val lessonDetailsUseCase: LessonDetailsUseCase
) : BaseViewModel<LessonDetailsViewState, LessonDetailsIntent, LessonDetailsAction>() {
    override val stateFlow: Flow<LessonDetailsViewState>
        get() = flowOf(LessonDetailsViewState.loadingInitial)

    override fun processAction(action: LessonDetailsAction) {
        when (action) {
            is LessonDetailsAction.LoadLessonDetailsAction ->
                lessonDetailsUseCase.loadLessonDetails(action.lessonId)
            is LessonDetailsAction.SetAttendanceAction ->
                lessonDetailsUseCase.setAttendance(action.lessonId, action.memberId)
            is LessonDetailsAction.RemoveAttendanceAction ->
                lessonDetailsUseCase.removeAttendance(action.lessonId, action.memberId)
        }
    }

}