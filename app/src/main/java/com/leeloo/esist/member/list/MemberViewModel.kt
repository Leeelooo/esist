package com.leeloo.esist.member.list

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MemberViewModel @ViewModelInject constructor(
    private val memberRepository: MemberRepository
) : BaseViewModel<MemberViewState, MemberIntent, MemberAction>() {
    override val stateFlow: Flow<MemberViewState>
        get() = flowOf(MemberViewState.initial)

    override suspend fun processAction(action: MemberAction) {
        when (action) {
            is MemberAction.LoadMemberAction -> memberRepository.getFilteredMembers(action.phrase)
            is MemberAction.OpenDialogAction -> memberRepository.openDialog()
            is MemberAction.DismissDialogAction -> memberRepository.dismissDialog()
            is MemberAction.AddMemberAction -> memberRepository.addMember(
                firstName = action.firstName,
                middleName = action.middleName,
                lastName = action.lastName,
                emailAddress = action.emailAddress
            )
        }
    }

}