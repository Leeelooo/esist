package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.vo.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GroupRepository : BaseRepository<GroupModelState> {
    fun getFilteredGroups(phrase: String)

    fun openDialog()
    fun dismissDialog()

    suspend fun addGroup(groupName: String)
}

@ExperimentalCoroutinesApi
class GroupRepositoryImpl @Inject constructor(
    private val groupLocalDataSource: GroupLocalDataSource
) : GroupRepository {
    private val modelStateFlow: MutableStateFlow<GroupModelState> =
        MutableStateFlow(GroupModelState.Initial)

    override fun modelStateFlow(): Flow<GroupModelState> = modelStateFlow

    override fun getFilteredGroups(phrase: String) {
        modelStateFlow.value = GroupModelState.Initial
        modelStateFlow.combine(
            groupLocalDataSource
                .getFilteredGroupsFlow(phrase)
        ) { _, groups ->
            GroupModelState.GroupsLoaded(groups)
        }
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