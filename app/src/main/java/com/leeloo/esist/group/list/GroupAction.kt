package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseAction

sealed class GroupAction : BaseAction {

    data class LoadGroupsAction(
        val phrase: String
    ) : GroupAction()

    object OpenDialogAction : GroupAction()

    object DismissDialogAction : GroupAction()

    data class AddGroupAction(
        val groupName: String
    ) : GroupAction()

}