package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupViewState, GroupIntent, GroupAction>() {
    override val stateFlow: Flow<GroupViewState>
        get() = flowOf(GroupViewState.initial)

    override suspend fun processAction(action: GroupAction) {
        when (action) {
            is GroupAction.LoadGroupsAction -> groupRepository.getFilteredGroups(action.phrase)
            is GroupAction.OpenDialogAction -> groupRepository.openDialog()
            is GroupAction.DismissDialogAction -> groupRepository.dismissDialog()
            is GroupAction.AddGroupAction -> groupRepository.addGroup(action.groupName)
        }
    }

}