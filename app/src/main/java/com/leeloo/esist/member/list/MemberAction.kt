package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseAction

sealed class MemberAction : BaseAction {

    data class LoadMemberAction(
        val phrase: String
    ) : MemberAction()

    object OpenDialogAction : MemberAction()

    object DismissDialogAction : MemberAction()

    data class AddMemberAction(
        val firstName: String,
        val middleName: String?,
        val lastName: String
    ) : MemberAction()

}