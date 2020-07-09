package com.leeloo.esist.group.details

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupDetailsViewModel @ViewModelInject constructor(
    private val groupDetailsRepository: GroupDetailsRepository
) : BaseViewModel<GroupDetailsViewState, GroupDetailsIntent, GroupDetailsAction>() {
    private var lastState: GroupDetailsViewState = GroupDetailsViewState.loadingInitial

    override val stateFlow: Flow<GroupDetailsViewState>
        get() = groupDetailsRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: GroupDetailsAction) {
        when (action) {
            is GroupDetailsAction.InitialIntent ->
                groupDetailsRepository.initial()
            is GroupDetailsAction.LoadGroupDetailsAction ->
                groupDetailsRepository.getGroupDetails(action.groupId)
            is GroupDetailsAction.FabClickAction ->
                groupDetailsRepository.openFabOptions()
            is GroupDetailsAction.DialogDismissAction ->
                groupDetailsRepository.dismissDialog()
            is GroupDetailsAction.LoadMembersToAddAction ->
                groupDetailsRepository.getMembersToAdd(action.groupId)
            is GroupDetailsAction.LoadLessonsToAddAction ->
                groupDetailsRepository.getLessonsToAdd(action.groupId)
            is GroupDetailsAction.ChooseMemberToAdd ->
                groupDetailsRepository.addMemberToGroup(action.memberId, action.groupId)
            is GroupDetailsAction.ChooseLessonToAdd ->
                groupDetailsRepository.addLessonToGroup(action.lessonId, action.groupId)
            is GroupDetailsAction.StatisticAction ->
                groupDetailsRepository.getAttendance(groupId = action.groupId)
        }
    }

}