package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseAction

sealed class GroupAction : BaseAction {

    object LoadGroupsAction : GroupAction()

    object OpenDialogAction : GroupAction()

    object DismissDialogAction : GroupAction()

    data class CheckMemberAction(
        val memberId: Long
    ) : GroupAction()

    data class CheckLessonAction(
        val lessonId: Long
    ) : GroupAction()

    data class AddGroupAction(
        val groupName: String
    ) : GroupAction()

}