package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseAction

sealed class MemberDetailsAction : BaseAction {

    object InitialAction : MemberDetailsAction()

    data class LoadMemberDetailsAction(
        val memberId: Long
    ) : MemberDetailsAction()

    data class MemberStatisticAction(
        val memberId: Long
    ) : MemberDetailsAction()

    object DismissDialogAction : MemberDetailsAction()

}