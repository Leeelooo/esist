package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MemberDetailsViewModel @Inject constructor(
    private val memberDetailsRepository: MemberDetailsRepository
) : BaseViewModel<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>() {
    override val stateFlow: Flow<MemberDetailsViewState>
        get() = flowOf(MemberDetailsViewState.initialLoading)

    override suspend fun processAction(action: MemberDetailsAction) {
        when (action) {
            is MemberDetailsAction.LoadMemberDetailsAction ->
                memberDetailsRepository.loadLessonDetails(action.memberId)
        }
    }

}