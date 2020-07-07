package com.leeloo.esist.group.list

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupViewModel @ViewModelInject constructor(
    private val groupRepository: GroupRepository
) : BaseViewModel<GroupViewState, GroupIntent, GroupAction>() {
    private var lastState: GroupViewState = GroupViewState.initial

    override val stateFlow: Flow<GroupViewState>
        get() = groupRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: GroupAction) {
        when (action) {
            is GroupAction.LoadGroupsAction -> groupRepository.getGroups()
            is GroupAction.OpenDialogAction -> groupRepository.openDialog()
            is GroupAction.DismissDialogAction -> groupRepository.dismissDialog()
            is GroupAction.AddGroupAction -> groupRepository.addGroup(
                action.groupName,
                lastState.selectedLessons,
                lastState.selectedMembers
            )
            is GroupAction.CheckLessonAction -> groupRepository.checkLesson(action.lessonId)
            is GroupAction.CheckMemberAction -> groupRepository.checkMember(action.memberId)
        }
    }

}