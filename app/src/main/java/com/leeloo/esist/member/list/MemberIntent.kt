package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseIntent
import com.leeloo.esist.vo.Group

sealed class MemberIntent : BaseIntent<MemberAction> {

    object InitialIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.LoadMemberAction
    }

    object ReloadIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.LoadMemberAction
    }

    object OpenDialogIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.OpenDialogAction
    }

    class OnGroupClick(
        private val groupId: Long
    ) : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.GroupSelectedAction(groupId)
    }

    object DismissDialogIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.DismissDialogAction
    }

    class CreateMemberIntent(
        private val firstName: String,
        private val lastName: String,
        private val emailAddress: String
    ) : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction
            .AddMemberAction(
                firstName = firstName,
                lastName = lastName,
                emailAddress = emailAddress
            )
    }

}