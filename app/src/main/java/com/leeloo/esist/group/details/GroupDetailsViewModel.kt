package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GroupDetailsViewModel @Inject constructor(
    private val groupDetailsRepository: GroupDetailsRepository
) : BaseViewModel<GroupDetailsViewState, GroupDetailsIntent, GroupDetailsAction>() {
    override val stateFlow: Flow<GroupDetailsViewState>
        get() = flowOf(GroupDetailsViewState.loadingInitial)

    init {
        stateFlow.combine(groupDetailsRepository.modelStateFlow()) { oldViewState, modelState ->
            modelState.reduce(oldViewState)
        }
    }

    override suspend fun processAction(action: GroupDetailsAction) {
        when (action) {
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
        }
    }

}