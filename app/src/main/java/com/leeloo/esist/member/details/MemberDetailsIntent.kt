package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseIntent

sealed class MemberDetailsIntent : BaseIntent<MemberDetailsAction> {

    class LoadMemberDetailsIntent(
        private val memberId: Long
    ) : MemberDetailsIntent() {
        override fun convertToAction(): MemberDetailsAction =
            MemberDetailsAction.LoadMemberDetailsAction(memberId)
    }

    class ReloadMemberDetailsIntent(
        private val memberId: Long
    ) : MemberDetailsIntent() {
        override fun convertToAction(): MemberDetailsAction =
            MemberDetailsAction.LoadMemberDetailsAction(memberId)
    }

    object InitialIntent : MemberDetailsIntent() {
        override fun convertToAction(): MemberDetailsAction = MemberDetailsAction.InitialAction
    }

    class GetMemberStatisticIntent(
        private val memberId: Long
    ) : MemberDetailsIntent() {
        override fun convertToAction(): MemberDetailsAction =
            MemberDetailsAction.MemberStatisticAction(memberId)
    }

    object DismissIntent : MemberDetailsIntent() {
        override fun convertToAction(): MemberDetailsAction =
            MemberDetailsAction.DismissDialogAction
    }

}