package com.leeloo.esist.member.list

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemberViewModel @ViewModelInject constructor(
    private val memberRepository: MemberRepository
) : BaseViewModel<MemberViewState, MemberIntent, MemberAction>() {
    private var lastState: MemberViewState = MemberViewState.initial

    override val stateFlow: Flow<MemberViewState>
        get() = memberRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: MemberAction) {
        when (action) {
            is MemberAction.LoadMemberAction -> memberRepository.getMembers()
            is MemberAction.OpenDialogAction -> memberRepository.openDialog()
            is MemberAction.DismissDialogAction -> memberRepository.dismissDialog()
            is MemberAction.AddMemberAction -> memberRepository.addMember(
                firstName = action.firstName,
                lastName = action.lastName,
                emailAddress = action.emailAddress,
                selectedGroups = lastState.selectedGroups
            )
            is MemberAction.GroupSelectedAction -> memberRepository.groupSelected(action.groupId)
        }
    }

}