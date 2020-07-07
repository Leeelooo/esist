package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.member.MemberLocalDataSource
import com.leeloo.esist.utils.getRandomColor
import com.leeloo.esist.vo.Member
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MemberRepository : BaseRepository<MemberModelState> {
    suspend fun getMembers()

    suspend fun openDialog()
    fun dismissDialog()

    suspend fun groupSelected(groupId: Long)

    suspend fun addMember(
        firstName: String,
        lastName: String,
        emailAddress: String,
        selectedGroups: List<Long>
    )
}

@ExperimentalCoroutinesApi
class MemberRepositoryImpl(
    private val memberLocalDataSource: MemberLocalDataSource,
    private val groupLocalDataSource: GroupLocalDataSource
) : MemberRepository {
    private val modelStateFlow: MutableStateFlow<MemberModelState> =
        MutableStateFlow(MemberModelState.Initial)

    override fun modelStateFlow(): Flow<MemberModelState> = modelStateFlow

    override suspend fun getMembers() {
        modelStateFlow.value = MemberModelState.Initial
        modelStateFlow.value =
            MemberModelState.MembersLoaded(memberLocalDataSource.getAllMembers())
    }

    override suspend fun openDialog() {
        modelStateFlow.value = MemberModelState.DialogOpen(groupLocalDataSource.getGroups())
    }

    override fun dismissDialog() {
        modelStateFlow.value = MemberModelState.DialogDismiss
    }

    override suspend fun groupSelected(groupId: Long) {
        modelStateFlow.value = MemberModelState.GroupSelected(groupId)
    }

    override suspend fun addMember(
        firstName: String,
        lastName: String,
        emailAddress: String,
        selectedGroups: List<Long>
    ) {
        val member = Member(
            firstName = firstName,
            lastName = lastName,
            emailAddress = emailAddress,
            memberColor = getRandomColor()
        )
        try {
            if (memberLocalDataSource.createMember(member, selectedGroups)) {
                modelStateFlow.value = MemberModelState.MemberInserted
                modelStateFlow.value =
                    MemberModelState.MembersLoaded(memberLocalDataSource.getAllMembers())
            } else {
                modelStateFlow.value = MemberModelState.MemberInsertionError(Exception("Error"))
            }
        } catch (e: Exception) {
            modelStateFlow.value = MemberModelState.MemberInsertionError(e)
        }
    }

}