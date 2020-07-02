package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseIntent

sealed class GroupIntent : BaseIntent<GroupAction> {

    object InitialIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.LoadGroupsAction("")
    }

    class ReloadIntent(
        private val phrase: String
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.LoadGroupsAction(phrase)
    }

    class ChangeFilterPhraseIntent(
        private val phrase: String
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.LoadGroupsAction(phrase)
    }

    object OpenDialogIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.OpenDialogAction
    }

    object DismissDialogIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.DismissDialogAction
    }

    class CreateGroupIntent(
        private val groupName: String
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.AddGroupAction(groupName)
    }

}