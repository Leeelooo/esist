package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseAction

sealed class MemberDetailsAction : BaseAction {

    data class LoadMemberDetailsAction(
        val memberId: Long
    ) : MemberDetailsAction()

}