package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    private val groupUseCase: GroupUseCase
) : BaseViewModel<GroupViewState, GroupIntent, GroupAction>() {
    override val stateFlow: Flow<GroupViewState>
        get() = flowOf(GroupViewState.initial)

    override fun processAction(action: GroupAction) {
        when (action) {
            is GroupAction.LoadGroupsAction -> groupUseCase.getFilteredGroups(action.phrase)
            is GroupAction.OpenDialogAction -> groupUseCase.openDialog()
            is GroupAction.DismissDialogAction -> groupUseCase.dismissDialog()
            is GroupAction.AddGroupAction -> groupUseCase.addGroup(action.groupName)
        }
    }

}