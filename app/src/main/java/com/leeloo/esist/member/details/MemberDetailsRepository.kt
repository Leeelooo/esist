package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.member.MemberLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface MemberDetailsRepository : BaseRepository<MemberDetailsModelState> {
    fun loadMemberDetails(memberId: Long)
}

@ExperimentalCoroutinesApi
class MemberDetailsRepositoryImpl @Inject constructor(
    private val memberLocalDataSource: MemberLocalDataSource
) : MemberDetailsRepository {
    private val modelStateFlow: MutableStateFlow<MemberDetailsModelState> =
        MutableStateFlow(MemberDetailsModelState.InitialLoading)

    override fun modelStateFlow(): Flow<MemberDetailsModelState> = modelStateFlow

    override fun loadMemberDetails(memberId: Long) {
        modelStateFlow.value = MemberDetailsModelState.InitialLoading
        modelStateFlow.combine(
            memberLocalDataSource.getMemberDetailsFlow(memberId)
        ) { _, memberDetails ->
            if (memberDetails != null) {
                MemberDetailsModelState.MemberDetailsLoaded(memberDetails)
            } else {
                MemberDetailsModelState.MemberDetailsLoadingError(Exception("Error"))
            }
        }
    }

}