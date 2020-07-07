package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.Lesson

sealed class LessonModelState : BaseModelState<LessonViewState> {

    object Initial : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState = LessonViewState.initial
    }

    class ErrorInitialLoading(
        private val error: Throwable
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.loadingError(error)
    }

    class LessonsLoaded(
        private val lessons: List<Lesson>
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.loadedLessons(lessons)
    }

    class DialogOpen(
        private val groups: List<Group>
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.openDialog(oldState.lessons, groups)
    }

    object DialogDismiss : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.loadedLessons(oldState.lessons)
    }

    class GroupSelected(
        private val groupId: Long
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.groupSelected(
                oldState.lessons,
                oldState.groupToAdd,
                oldState.selectedGroups.toMutableSet().apply {
                    if (contains(groupId)) {
                        remove(groupId)
                    } else {
                        add(groupId)
                    }
                }.toList()
            )
    }

    object LessonInserted : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.lessonInserted(oldState.lessons)
    }

    class LessonInsetionError(
        private val error: Throwable
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.lessonInsertionError(
                oldState.lessons,
                oldState.groupToAdd,
                oldState.selectedGroups,
                error
            )
    }

}