package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseIntent

sealed class GroupDetailsIntent : BaseIntent<GroupDetailsAction> {

    object InitialIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.InitialIntent
    }

    class GroupDetailsLoading(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadGroupDetailsAction(groupId)
    }

    class ReloadGroupIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadGroupDetailsAction(groupId)
    }

    object AddIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.FabClickAction
    }

    object DismissDialogIntent : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.DialogDismissAction
    }

    class AddMemberIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadMembersToAddAction(groupId)
    }

    class AddLessonIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadLessonsToAddAction(groupId)
    }

    class ReloadMemberIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadMembersToAddAction(groupId)
    }

    class ReloadLessonIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.LoadLessonsToAddAction(groupId)
    }

    class ChooseMemberIntent(
        private val groupId: Long,
        private val memberId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.ChooseMemberToAdd(groupId, memberId)
    }

    class ChooseLessonIntent(
        private val groupId: Long,
        private val lessonId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.ChooseLessonToAdd(groupId, lessonId)
    }

    class CheckStatisticIntent(
        private val groupId: Long
    ) : GroupDetailsIntent() {
        override fun convertToAction(): GroupDetailsAction =
            GroupDetailsAction.StatisticAction(groupId)
    }

}