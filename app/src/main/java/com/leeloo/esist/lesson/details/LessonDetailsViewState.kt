package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.LessonDetails

data class LessonDetailsViewState(
    val loadingLessonDetails: Boolean,
    val lessonDetails: LessonDetails?,
    val loadingLessonDetailsError: Throwable?,
    val attendance: Attendance?
) : BaseViewState {
    companion object {
        val loadingInitial: LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = true,
            lessonDetails = null,
            loadingLessonDetailsError = null,
            attendance = null
        )

        fun lessonDetailsLoaded(
            lessonDetails: LessonDetails?
        ): LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = false,
            lessonDetails = lessonDetails,
            loadingLessonDetailsError = null,
            attendance = null
        )

        fun lessonDetailsLoadingError(
            loadingLessonDetailsError: Throwable
        ): LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = false,
            lessonDetails = null,
            loadingLessonDetailsError = loadingLessonDetailsError,
            attendance = null
        )

        fun showAttendance(
            lessonDetails: LessonDetails?,
            attendance: Attendance?
        ): LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = false,
            lessonDetails = lessonDetails,
            loadingLessonDetailsError = null,
            attendance = attendance
        )
    }
}