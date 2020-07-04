package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.member.MemberLocalDataSource
import com.leeloo.esist.vo.Member
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface MemberRepository : BaseRepository<MemberModelState> {
    fun getFilteredMembers(phrase: String)

    fun openDialog()
    fun dismissDialog()

    suspend fun addMember(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String
    )
}

@ExperimentalCoroutinesApi
class MemberRepositoryImpl @Inject constructor(
    private val memberLocalDataSource: MemberLocalDataSource
) : MemberRepository {
    private val modelStateFlow: MutableStateFlow<MemberModelState> =
        MutableStateFlow(MemberModelState.Initial)

    override fun modelStateFlow(): Flow<MemberModelState> = modelStateFlow

    override fun getFilteredMembers(phrase: String) {
        TODO("Not yet implemented")
    }

    override fun openDialog() {
        modelStateFlow.value = MemberModelState.DialogOpen
    }

    override fun dismissDialog() {
        modelStateFlow.value = MemberModelState.DialogDismiss
    }

    override suspend fun addMember(
        firstName: String,
        middleName: String?,
        lastName: String,
        emailAddress: String
    ) {
        val member = Member(
            firstName = firstName,
            middleName = middleName,
            lastName = lastName,
            emailAddress = emailAddress,
            memberColor = 0
        )//TODO: generate color
        if (memberLocalDataSource.createMember(member)) {
            modelStateFlow.value = MemberModelState.MemberInserted
        } else {
            modelStateFlow.value = MemberModelState.MemberInsertionError(Exception("Error"))
        }
    }

}