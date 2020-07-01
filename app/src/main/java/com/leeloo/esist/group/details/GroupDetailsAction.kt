package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseAction

sealed class GroupDetailsAction : BaseAction {

    class LoadGroupDetailsAction(
        val phrase: String
    ) : GroupDetailsAction()

    object FabClickAction : GroupDetailsAction()

    object LoadMembersToAddAction : GroupDetailsAction()

    object LoadLessonsToAddAction : GroupDetailsAction()

}