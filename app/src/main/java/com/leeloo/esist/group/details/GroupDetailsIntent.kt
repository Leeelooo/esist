package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseIntent

sealed class GroupDetailsIntent : BaseIntent<GroupDetailsAction> {

    object InitialIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadGroupDetailsAction("")
    }

    object ReloadGroupIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadGroupDetailsAction("")
    }

    class ChangeFilterPhrase(
        private val pharse: String
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadGroupDetailsAction(pharse)
    }

    object AddIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.FabClickAction
    }

    object AddMemberIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadMembersToAddAction
    }

    object AddLessonIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadLessonsToAddAction
    }

    object ReloadMemberIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadMembersToAddAction
    }

    object ReloadLessonIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadLessonsToAddAction
    }

}