package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.MemberDetails

data class MemberDetailsViewState(
    val loadingMemberDetails: Boolean,
    val memberDetails: MemberDetails?,
    val memberDetailsLoadingError: Throwable?,
    val isDialogOpen: Boolean,
    val visitedLessons: List<Lesson>,
    val pastLessons: List<Lesson>
) : BaseViewState {
    companion object {
        val initialLoading: MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = true,
            memberDetails = null,
            memberDetailsLoadingError = null,
            isDialogOpen = false,
            visitedLessons = emptyList(),
            pastLessons = emptyList()
        )

        fun memberDetailsLoaded(
            memberDetails: MemberDetails?
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = memberDetails,
            memberDetailsLoadingError = null,
            isDialogOpen = false,
            visitedLessons = emptyList(),
            pastLessons = emptyList()
        )

        fun lessonDetailsLoadingError(
            memberDetailsLoadingError: Throwable
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = null,
            memberDetailsLoadingError = memberDetailsLoadingError,
            isDialogOpen = false,
            visitedLessons = emptyList(),
            pastLessons = emptyList()
        )

        fun lessonDetailsStatistic(
            memberDetails: MemberDetails?,
            visitedLessons: List<Lesson>,
            pastLessons: List<Lesson>
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = memberDetails,
            memberDetailsLoadingError = null,
            isDialogOpen = true,
            visitedLessons = visitedLessons,
            pastLessons = pastLessons
        )

    }
}