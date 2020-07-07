package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseAction
import com.leeloo.esist.vo.Group

sealed class MemberAction : BaseAction {

    object LoadMemberAction : MemberAction()

    object OpenDialogAction : MemberAction()

    object DismissDialogAction : MemberAction()

    data class GroupSelectedAction(
        val groupId: Long
    ) : MemberAction()

    data class AddMemberAction(
        val firstName: String,
        val lastName: String,
        val emailAddress: String
    ) : MemberAction()

}