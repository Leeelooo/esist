package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseIntent

sealed class MemberIntent : BaseIntent<MemberAction> {

    object InitialIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.LoadMemberAction("")
    }

    class ReloadIntent(
        private val phrase: String
    ) : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.LoadMemberAction(phrase)
    }

    class ChangeFilterPhraseIntent(
        private val phrase: String
    ) : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.LoadMemberAction(phrase)
    }

    object OpenDialogIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.OpenDialogAction
    }

    object DismissDialogIntent : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction.DismissDialogAction
    }

    class CreateGroupIntent(
        private val firstName: String,
        private val middleName: String?,
        private val lastName: String,
        private val emailAddress: String
    ) : MemberIntent() {
        override fun convertToAction(): MemberAction = MemberAction
            .AddMemberAction(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                emailAddress = emailAddress
            )
    }

}