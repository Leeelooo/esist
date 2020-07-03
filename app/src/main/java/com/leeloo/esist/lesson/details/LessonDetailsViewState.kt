package com.leeloo.esist.lesson.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.LessonDetails

data class LessonDetailsViewState(
    val loadingLessonDetails: Boolean,
    val lessonDetails: LessonDetails?,
    val loadingLessonDetailsError: Throwable?
) : BaseViewState {
    companion object {
        val loadingInitial: LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = true,
            lessonDetails = null,
            loadingLessonDetailsError = null
        )

        fun lessonDetailsLoaded(
            lessonDetails: LessonDetails?
        ): LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = false,
            lessonDetails = lessonDetails,
            loadingLessonDetailsError = null
        )

        fun lessonDetailsLoadingError(
            loadingLessonDetailsError: Throwable
        ): LessonDetailsViewState = LessonDetailsViewState(
            loadingLessonDetails = false,
            lessonDetails = null,
            loadingLessonDetailsError = loadingLessonDetailsError
        )

    }
}