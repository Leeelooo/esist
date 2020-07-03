package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MemberViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase
) : BaseViewModel<MemberViewState, MemberIntent, MemberAction>() {
    override val stateFlow: Flow<MemberViewState>
        get() = flowOf(MemberViewState.initial)

    override fun processAction(action: MemberAction) {
        when (action) {
            is MemberAction.LoadMemberAction -> memberUseCase.getFilteredMembers(action.phrase)
            is MemberAction.OpenDialogAction -> memberUseCase.openDialog()
            is MemberAction.DismissDialogAction -> memberUseCase.dismissDialog()
            is MemberAction.AddMemberAction -> memberUseCase.addMember(
                firstName = action.firstName,
                middleName = action.middleName,
                lastName = action.lastName
            )
        }
    }

}