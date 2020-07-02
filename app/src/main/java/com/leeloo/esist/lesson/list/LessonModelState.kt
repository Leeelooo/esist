package com.leeloo.esist.lesson.list

import com.leeloo.esist.base.BaseModelState
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

    object DialogOpen : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.openDialog(oldState.lessons)
    }

    object DialogDismiss : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.loadedLessons(oldState.lessons)
    }

    object LessonInserted : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.lessonInserted(oldState.lessons)
    }

    class LessonInsetionError(
        private val error: Throwable
    ) : LessonModelState() {
        override fun reduce(oldState: LessonViewState): LessonViewState =
            LessonViewState.lessonInsertionError(oldState.lessons, error)
    }

}