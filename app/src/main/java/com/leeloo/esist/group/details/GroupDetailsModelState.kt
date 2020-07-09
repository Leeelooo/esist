package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.*

sealed class GroupDetailsModelState : BaseModelState<GroupDetailsViewState> {

    object Loading : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadingInitial
    }

    class LoadedDetails(
        private val groupDetails: GroupDetails
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadedDetails(groupDetails)
    }

    class DetailsLoadingError(
        private val error: Throwable
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadedDetailsFailed(error)
    }

    object FabClick : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.changeFabState(oldState.group, true)
    }

    object DialogDismiss : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.changeFabState(oldState.group, false)
    }

    object MembersLoading : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadingMembersToAdd(oldState.group)
    }

    class MembersLoaded(
        private val members: List<Member>
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadedMembersToAdd(oldState.group, members)
    }

    class MembersLoadingFailed(
        private val membersLoadingError: Throwable
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.membersLoadingFailed(oldState.group, membersLoadingError)
    }

    object LessonsLoading : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadingLessonsToAdd(oldState.group)
    }

    class LessonsLoaded(
        private val lessons: List<Lesson>
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.loadedLessonsToAdd(oldState.group, lessons)
    }

    class LessonsLoadingFailed(
        private val lessonsLoadingError: Throwable
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.lessonsLoadingFailed(oldState.group, lessonsLoadingError)
    }

    class AttendanceLoaded(
        private val attendance: Attendance
    ) : GroupDetailsModelState() {
        override fun reduce(oldState: GroupDetailsViewState): GroupDetailsViewState =
            GroupDetailsViewState.showAttendance(oldState.group, attendance)
    }

}