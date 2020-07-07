package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseIntent

sealed class GroupIntent : BaseIntent<GroupAction> {

    object InitialIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.LoadGroupsAction
    }

    object ReloadIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.LoadGroupsAction
    }

    object OpenDialogIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.OpenDialogAction
    }

    object DismissDialogIntent : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.DismissDialogAction
    }

    class CheckMemberIntent(
        private val memberId: Long
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.CheckMemberAction(memberId)
    }

    class CheckLessonIntent(
        private val lessonId: Long
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.CheckLessonAction(lessonId)
    }

    class CreateGroupIntent(
        private val groupName: String
    ) : GroupIntent() {
        override fun convertToAction(): GroupAction = GroupAction.AddGroupAction(groupName)
    }

}