package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.Lesson

data class LessonViewState(
    val loading: Boolean,
    val lessons: List<Lesson>,
    val error: Throwable?,
    val isDialogOpened: Boolean,
    val groupToAdd: List<Group>,
    val selectedGroups: List<Long>,
    val isLessonInserted: Boolean,
    val lessonInsertionError: Throwable?
) : BaseViewState {

    companion object {
        val initial: LessonViewState = LessonViewState(
            loading = true,
            lessons = emptyList(),
            error = null,
            isDialogOpened = false,
            groupToAdd = emptyList(),
            selectedGroups = emptyList(),
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun loadedLessons(lessons: List<Lesson>): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = false,
            groupToAdd = emptyList(),
            selectedGroups = emptyList(),
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun loadingError(error: Throwable): LessonViewState = LessonViewState(
            loading = false,
            lessons = emptyList(),
            error = error,
            isDialogOpened = false,
            groupToAdd = emptyList(),
            selectedGroups = emptyList(),
            isLessonInserted = false,
            lessonInsertionError = null
        )

        fun openDialog(lessons: List<Lesson>, groupToAdd: List<Group>): LessonViewState =
            LessonViewState(
                loading = false,
                lessons = lessons,
                error = null,
                isDialogOpened = true,
                groupToAdd = groupToAdd,
                selectedGroups = emptyList(),
                isLessonInserted = false,
                lessonInsertionError = null
            )

        fun groupSelected(
            lessons: List<Lesson>,
            groupToAdd: List<Group>,
            selectedGroups: List<Long>
        ): LessonViewState =
            LessonViewState(
                loading = false,
                lessons = lessons,
                error = null,
                isDialogOpened = true,
                groupToAdd = groupToAdd,
                selectedGroups = selectedGroups,
                isLessonInserted = false,
                lessonInsertionError = null
            )

        fun lessonInserted(lessons: List<Lesson>): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = false,
            groupToAdd = emptyList(),
            selectedGroups = emptyList(),
            isLessonInserted = true,
            lessonInsertionError = null
        )

        fun lessonInsertionError(
            lessons: List<Lesson>,
            groupToAdd: List<Group>,
            selectedGroups: List<Long>,
            lessonInsertionError: Throwable
        ): LessonViewState = LessonViewState(
            loading = false,
            lessons = lessons,
            error = null,
            isDialogOpened = true,
            groupToAdd = groupToAdd,
            selectedGroups = selectedGroups,
            isLessonInserted = false,
            lessonInsertionError = lessonInsertionError
        )

    }
}