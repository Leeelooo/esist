package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.vo.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface GroupRepository : BaseRepository<GroupModelState> {
    suspend fun getFilteredGroups(phrase: String)

    fun openDialog()
    fun dismissDialog()

    suspend fun addGroup(groupName: String)
}

@ExperimentalCoroutinesApi
class GroupRepositoryImpl(
    private val groupLocalDataSource: GroupLocalDataSource
) : GroupRepository {
    private val modelStateFlow: MutableStateFlow<GroupModelState> =
        MutableStateFlow(GroupModelState.Initial)

    override fun modelStateFlow(): Flow<GroupModelState> = modelStateFlow

    override suspend fun getFilteredGroups(phrase: String) {
        modelStateFlow.value = GroupModelState.Initial
        modelStateFlow.value =
            GroupModelState.GroupsLoaded(groupLocalDataSource.getFilteredGroups(phrase))
    }

    override fun openDialog() {
        modelStateFlow.value = GroupModelState.DialogOpen
    }

    override fun dismissDialog() {
        modelStateFlow.value = GroupModelState.DialogDismiss
    }

    //TODO: color generator
    override suspend fun addGroup(groupName: String) {
        modelStateFlow.value =
            if (groupLocalDataSource.createGroup(Group(groupName = groupName, groupColor = 1))) {
                GroupModelState.GroupInserted
            } else {
                GroupModelState.GroupInsetionError(Exception("Error"))
            }
    }

}