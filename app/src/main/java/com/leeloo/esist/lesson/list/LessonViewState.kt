package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Lesson

data class LessonViewState(
    val loading: Boolean,
    val lessons: List<Lesson>,
    val error: Throwable?,
    val isDialogOpened: Boolean,
    val isLessonInserted: Boolean,
    val lessonInsertionError: Throwable?
) : BaseViewState {

    companion object {
        val initial: LessonViewState = LessonViewState(
            loading = true,
            lessons = emptyList(),
            error = null,
            isDialogOpened = false,
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun loadedLessons(lessons: List<Lesson>): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = false,
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun loadingError(error: Throwable): LessonViewState = LessonViewState(
            loading = false,
            lessons = emptyList(),
            error = error,
            isDialogOpened = false,
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun openDialog(lessons: List<Lesson>): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = true,
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun lessonInserted(lessons: List<Lesson>): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = false,
            isLessonInserted = true,
            lessonInsertionError = null
        )

        fun lessonInsertionError(
            lessons: List<Lesson>,
            lessonInsertionError: Throwable
        ): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = false,
            isLessonInserted = false,
            lessonInsertionError = lessonInsertionError
        )

    }
}