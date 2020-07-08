package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseAction

sealed class GroupDetailsAction : BaseAction {

    data class LoadGroupDetailsAction(
        val groupId: Long
    ) : GroupDetailsAction()

    object FabClickAction : GroupDetailsAction()

    object DialogDismissAction : GroupDetailsAction()

    object InitialIntent : GroupDetailsAction()

    data class LoadMembersToAddAction(
        val groupId: Long
    ) : GroupDetailsAction()

    data class LoadLessonsToAddAction(
        val groupId: Long
    ) : GroupDetailsAction()

    data class ChooseMemberToAdd(
        val groupId: Long,
        val memberId: Long
    ) : GroupDetailsAction()

    data class ChooseLessonToAdd(
        val groupId: Long,
        val lessonId: Long
    ) : GroupDetailsAction()

}