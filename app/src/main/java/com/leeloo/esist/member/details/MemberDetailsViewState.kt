package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.MemberDetails

data class MemberDetailsViewState(
    val loadingMemberDetails: Boolean,
    val memberDetails: MemberDetails?,
    val memberDetailsLoadingError: Throwable?,
    val isDialogOpen: Boolean,
    val attendance: Attendance?
) : BaseViewState {
    companion object {
        val initialLoading: MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = true,
            memberDetails = null,
            memberDetailsLoadingError = null,
            isDialogOpen = false,
            attendance = null
        )

        fun memberDetailsLoaded(
            memberDetails: MemberDetails?
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = memberDetails,
            memberDetailsLoadingError = null,
            isDialogOpen = false,
            attendance = null
        )

        fun lessonDetailsLoadingError(
            memberDetailsLoadingError: Throwable
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = null,
            memberDetailsLoadingError = memberDetailsLoadingError,
            isDialogOpen = false,
            attendance = null
        )

        fun lessonDetailsStatistic(
            memberDetails: MemberDetails?,
            attendance: Attendance
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = memberDetails,
            memberDetailsLoadingError = null,
            isDialogOpen = true,
            attendance = attendance
        )

    }
}