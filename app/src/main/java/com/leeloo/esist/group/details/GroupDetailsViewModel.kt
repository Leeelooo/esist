package com.leeloo.esist.group.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupDetailsViewModel @Inject constructor(
    private val groupDetailsUseCase: GroupDetailsUseCase
) : BaseViewModel<GroupDetailsViewState, GroupDetailsIntent, GroupDetailsAction>() {
    override val stateFlow: Flow<GroupDetailsViewState>
        get() = flowOf(GroupDetailsViewState.loadingInitial)

    init {
        stateFlow.combine(groupDetailsUseCase.getModelStateFlow()) { oldViewState, modelState ->
            modelState.reduce(oldViewState)
        }
    }

    override fun processAction(action: GroupDetailsAction) {
        when (action) {
            is GroupDetailsAction.LoadGroupDetailsAction ->
                groupDetailsUseCase.getGroupDetails(action.groupId)
            is GroupDetailsAction.FabClickAction ->
                groupDetailsUseCase.openFabOptions()
            is GroupDetailsAction.DialogDismissAction ->
                groupDetailsUseCase.dismissDialog()
            is GroupDetailsAction.LoadMembersToAddAction ->
                groupDetailsUseCase.getMembersToAdd(action.groupId)
            is GroupDetailsAction.LoadLessonsToAddAction ->
                groupDetailsUseCase.getLessonsToAdd(action.groupId)
            is GroupDetailsAction.ChooseMemberToAdd ->
                groupDetailsUseCase.addMemberToGroup(action.memberId, action.groupId)
            is GroupDetailsAction.ChooseLessonToAdd ->
                groupDetailsUseCase.addLessonToGroup(action.lessonId, action.groupId)
        }
    }

}