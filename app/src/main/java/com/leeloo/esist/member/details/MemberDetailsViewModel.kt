package com.leeloo.esist.member.details

import androidx.hilt.lifecycle.ViewModelInject
import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemberDetailsViewModel @ViewModelInject constructor(
    private val memberDetailsRepository: MemberDetailsRepository
) : BaseViewModel<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>() {
    private var lastState: MemberDetailsViewState = MemberDetailsViewState.initialLoading

    override val stateFlow: Flow<MemberDetailsViewState>
        get() = memberDetailsRepository.modelStateFlow().map {
            val newState = it.reduce(lastState)
            lastState = newState
            newState
        }

    override suspend fun processAction(action: MemberDetailsAction) {
        when (action) {
            is MemberDetailsAction.LoadMemberDetailsAction ->
                memberDetailsRepository.loadMemberDetails(action.memberId)
        }
    }

}