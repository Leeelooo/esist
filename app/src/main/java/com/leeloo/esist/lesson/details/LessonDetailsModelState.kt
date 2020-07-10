package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.db.dao.AttendanceDao
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.LessonDetails

sealed class LessonDetailsModelState : BaseModelState<LessonDetailsViewState> {

    object InitialLoading : LessonDetailsModelState() {
        override fun reduce(oldState: LessonDetailsViewState): LessonDetailsViewState =
            LessonDetailsViewState.loadingInitial
    }

    class LessonDetailsLoaded(
        private val lessonDetails: LessonDetails
    ) : LessonDetailsModelState() {
        override fun reduce(oldState: LessonDetailsViewState): LessonDetailsViewState =
            LessonDetailsViewState.lessonDetailsLoaded(lessonDetails)
    }

    class LessonDetailsLoadingError(
        private val lessonDetailsLoadingError: Throwable
    ) : LessonDetailsModelState() {
        override fun reduce(oldState: LessonDetailsViewState): LessonDetailsViewState =
            LessonDetailsViewState.lessonDetailsLoadingError(lessonDetailsLoadingError)
    }

    class LessonDetailsAttendance(
        private val attendance: Attendance
    ) : LessonDetailsModelState() {
        override fun reduce(oldState: LessonDetailsViewState): LessonDetailsViewState =
            LessonDetailsViewState.showAttendance(oldState.lessonDetails, attendance)
    }

    object DismissDialog : LessonDetailsModelState() {
        override fun reduce(oldState: LessonDetailsViewState): LessonDetailsViewState =
            LessonDetailsViewState.lessonDetailsLoaded(oldState.lessonDetails)
    }

}