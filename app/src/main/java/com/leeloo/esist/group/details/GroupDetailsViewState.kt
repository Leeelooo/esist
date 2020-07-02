package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.GroupDetails
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.Member

data class GroupDetailsViewState(
    val loading: Boolean,
    val group: GroupDetails?,
    val error: Throwable?,
    val isFabOpened: Boolean,
    val membersLoading: Boolean,
    val membersToAdd: List<Member>,
    val membersLoadingError: Throwable?,
    val lessonsLoading: Boolean,
    val lessonsToAdd: List<Lesson>,
    val lessonsLoadingError: Throwable?
) : BaseViewState {
    companion object {
        val loadingInitial: GroupDetailsViewState
            get() = GroupDetailsViewState(
                loading = true,
                group = null,
                error = null,
                isFabOpened = false,
                membersLoading = false,
                membersToAdd = emptyList(),
                membersLoadingError = null,
                lessonsLoading = false,
                lessonsToAdd = emptyList(),
                lessonsLoadingError = null
            )

        fun loadedDetails(
            group: GroupDetails
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun loadedDetailsFailed(
            error: Throwable
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = null,
            error = error,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun changeFabState(
            group: GroupDetails?,
            state: Boolean
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = state,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun loadingMembersToAdd(
            group: GroupDetails?
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = true,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun loadedMembersToAdd(
            group: GroupDetails?,
            membersToAdd: List<Member>
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = membersToAdd,
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun membersLoadingFailed(
            group: GroupDetails?,
            membersLoadingError: Throwable?
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = membersLoadingError,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun loadingLessonsToAdd(
            group: GroupDetails?
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = true,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = null
        )

        fun loadedLessonsToAdd(
            group: GroupDetails?,
            lessonsToAdd: List<Lesson>
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = lessonsToAdd,
            lessonsLoadingError = null
        )

        fun lessonsLoadingFailed(
            group: GroupDetails?,
            lessonsLoadingError: Throwable?
        ): GroupDetailsViewState = GroupDetailsViewState(
            loading = false,
            group = group,
            error = null,
            isFabOpened = false,
            membersLoading = false,
            membersToAdd = emptyList(),
            membersLoadingError = null,
            lessonsLoading = false,
            lessonsToAdd = emptyList(),
            lessonsLoadingError = lessonsLoadingError
        )

    }
}