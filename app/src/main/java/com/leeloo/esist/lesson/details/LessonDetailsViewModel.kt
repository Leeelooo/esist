package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LessonDetailsViewModel @Inject constructor(
    private val lessonDetailsRepository: LessonDetailsRepository
) : BaseViewModel<LessonDetailsViewState, LessonDetailsIntent, LessonDetailsAction>() {
    override val stateFlow: Flow<LessonDetailsViewState>
        get() = flowOf(LessonDetailsViewState.loadingInitial)

    override suspend fun processAction(action: LessonDetailsAction) {
        when (action) {
            is LessonDetailsAction.LoadLessonDetailsAction ->
                lessonDetailsRepository.loadLessonDetails(action.lessonId)
            is LessonDetailsAction.SetAttendanceAction ->
                lessonDetailsRepository.setAttendance(action.lessonId, action.memberId)
            is LessonDetailsAction.RemoveAttendanceAction ->
                lessonDetailsRepository.removeAttendance(action.lessonId, action.memberId)
        }
    }

}