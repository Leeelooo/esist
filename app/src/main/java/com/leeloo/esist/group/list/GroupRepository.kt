package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.member.MemberLocalDataSource
import com.leeloo.esist.utils.getRandomColor
import com.leeloo.esist.vo.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface GroupRepository : BaseRepository<GroupModelState> {
    suspend fun getGroups()

    suspend fun openDialog()
    fun dismissDialog()
    fun checkMember(memberId: Long)
    fun checkLesson(lessonId: Long)

    suspend fun addGroup(
        groupName: String,
        selectedLessons: List<Long>,
        selectedMembers: List<Long>
    )
}

@ExperimentalCoroutinesApi
class GroupRepositoryImpl(
    private val groupLocalDataSource: GroupLocalDataSource,
    private val memberLocalDataSource: MemberLocalDataSource,
    private val lessonLocalDataSource: LessonLocalDataSource
) : GroupRepository {
    private val modelStateFlow: MutableStateFlow<GroupModelState> =
        MutableStateFlow(GroupModelState.Initial)

    override fun modelStateFlow(): Flow<GroupModelState> = modelStateFlow

    override suspend fun getGroups() {
        modelStateFlow.value = GroupModelState.Initial
        modelStateFlow.value =
            GroupModelState.GroupsLoaded(groupLocalDataSource.getGroups())
    }

    override suspend fun openDialog() {
        modelStateFlow.value = GroupModelState.DialogOpen(
            lessonLocalDataSource.getLessons(),
            memberLocalDataSource.getAllMembers()
        )
    }

    override fun dismissDialog() {
        modelStateFlow.value = GroupModelState.DialogDismiss
    }

    override fun checkMember(memberId: Long) {
        modelStateFlow.value = GroupModelState.MemberChecked(memberId)
    }

    override fun checkLesson(lessonId: Long) {
        modelStateFlow.value = GroupModelState.LessonChecked(lessonId)
    }

    override suspend fun addGroup(
        groupName: String,
        selectedLessons: List<Long>,
        selectedMembers: List<Long>
    ) {
        try {
            if (groupLocalDataSource.createGroup(
                    Group(
                        groupName = groupName,
                        groupColor = getRandomColor()
                    ),
                    selectedLessons,
                    selectedMembers
                )
            ) {
                modelStateFlow.value = GroupModelState.GroupInserted
                modelStateFlow.value =
                    GroupModelState.GroupsLoaded(groupLocalDataSource.getGroups())
            } else {

                modelStateFlow.value = GroupModelState.GroupInsetionError(Exception("Error"))
            }
        } catch (e: Exception) {
            GroupModelState.GroupInsetionError(e)
        }
    }

}