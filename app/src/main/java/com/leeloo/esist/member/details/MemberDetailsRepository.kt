package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.member.MemberLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MemberDetailsRepository : BaseRepository<MemberDetailsModelState> {
    suspend fun loadMemberDetails(memberId: Long)
    fun initial()
    suspend fun loadMemberStatistic(memberId: Long)
    fun dismissDialog()
}

@ExperimentalCoroutinesApi
class MemberDetailsRepositoryImpl(
    private val memberLocalDataSource: MemberLocalDataSource
) : MemberDetailsRepository {
    private val modelStateFlow: MutableStateFlow<MemberDetailsModelState> =
        MutableStateFlow(MemberDetailsModelState.InitialLoading)

    override fun modelStateFlow(): Flow<MemberDetailsModelState> = modelStateFlow

    override suspend fun loadMemberDetails(memberId: Long) {
        modelStateFlow.value = MemberDetailsModelState.InitialLoading
        val memberDetails = memberLocalDataSource.getMemberDetails(memberId)
        modelStateFlow.value =
            if (memberDetails != null) {
                MemberDetailsModelState.MemberDetailsLoaded(memberDetails)
            } else {
                MemberDetailsModelState.MemberDetailsLoadingError(Exception("Error"))
            }
    }

    override fun initial() {
        modelStateFlow.value = MemberDetailsModelState.InitialLoading
    }

    override suspend fun loadMemberStatistic(memberId: Long) {
        modelStateFlow.value = MemberDetailsModelState.MemberStatisticLoaded(
            memberLocalDataSource.getMemberStatistic(memberId)
        )
    }

    override fun dismissDialog() {
        modelStateFlow.value = MemberDetailsModelState.DismissDialog
    }


}