package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Attendance
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.MemberDetails

sealed class MemberDetailsModelState : BaseModelState<MemberDetailsViewState> {

    object InitialLoading : MemberDetailsModelState() {
        override fun reduce(oldState: MemberDetailsViewState): MemberDetailsViewState =
            MemberDetailsViewState.initialLoading
    }

    class MemberDetailsLoaded(
        private val memberDetails: MemberDetails
    ) : MemberDetailsModelState() {
        override fun reduce(oldState: MemberDetailsViewState): MemberDetailsViewState =
            MemberDetailsViewState.memberDetailsLoaded(memberDetails)
    }

    class MemberDetailsLoadingError(
        private val memberDetailsLoadingError: Throwable
    ) : MemberDetailsModelState() {
        override fun reduce(oldState: MemberDetailsViewState): MemberDetailsViewState =
            MemberDetailsViewState.lessonDetailsLoadingError(memberDetailsLoadingError)
    }

    class MemberStatisticLoaded(
        val attendance: Attendance
    ) : MemberDetailsModelState() {
        override fun reduce(oldState: MemberDetailsViewState): MemberDetailsViewState =
            MemberDetailsViewState.lessonDetailsStatistic(
                oldState.memberDetails,
                attendance
            )
    }

    object DismissDialog : MemberDetailsModelState() {
        override fun reduce(oldState: MemberDetailsViewState): MemberDetailsViewState =
            MemberDetailsViewState.memberDetailsLoaded(oldState.memberDetails)
    }

}