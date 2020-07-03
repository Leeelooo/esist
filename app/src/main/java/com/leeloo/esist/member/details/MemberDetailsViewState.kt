package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.MemberDetails

data class MemberDetailsViewState(
    val loadingMemberDetails: Boolean,
    val memberDetails: MemberDetails?,
    val memberDetailsLoadingError: Throwable?
) : BaseViewState {
    companion object {
        val initialLoading: MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = true,
            memberDetails = null,
            memberDetailsLoadingError = null
        )

        fun memberDetailsLoaded(
            memberDetails: MemberDetails?
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = memberDetails,
            memberDetailsLoadingError = null
        )

        fun lessonDetailsLoadingError(
            memberDetailsLoadingError: Throwable
        ): MemberDetailsViewState = MemberDetailsViewState(
            loadingMemberDetails = false,
            memberDetails = null,
            memberDetailsLoadingError = memberDetailsLoadingError
        )

    }
}