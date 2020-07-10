package com.leeloo.esist.lesson.details

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LessonDetailsViewModel @ViewModelInject constructor(
    private val lessonDetailsRepository: LessonDetailsRepository
) : BaseViewModel<LessonDetailsViewState, LessonDetailsIntent, LessonDetailsAction>() {
    private var lastState: LessonDetailsViewState = LessonDetailsViewState.loadingInitial

    override val stateFlow: Flow<LessonDetailsViewState>
        get() = lessonDetailsRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: LessonDetailsAction) {
        when (action) {
            is LessonDetailsAction.LoadLessonDetailsAction ->
                lessonDetailsRepository.loadLessonDetails(action.lessonId)
            is LessonDetailsAction.SetAttendanceAction ->
                lessonDetailsRepository.setAttendance(action.lessonId, action.memberId)
            is LessonDetailsAction.RemoveAttendanceAction ->
                lessonDetailsRepository.removeAttendance(action.lessonId, action.memberId)
            is LessonDetailsAction.InitialAction ->
                lessonDetailsRepository.initial()
            is LessonDetailsAction.AttendanceAction -> lessonDetailsRepository.getAttendance(action.lessonId)
            is LessonDetailsAction.DismissAction -> lessonDetailsRepository.dismissDialog()
        }
    }

}