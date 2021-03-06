package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseRepository
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.member.MemberLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface GroupDetailsRepository : BaseRepository<GroupDetailsModelState> {
    suspend fun getGroupDetails(groupId: Long)

    fun openFabOptions()
    fun dismissDialog()

    suspend fun getLessonsToAdd(groupId: Long)
    suspend fun getMembersToAdd(groupId: Long)

    suspend fun addMemberToGroup(memberId: Long, groupId: Long)
    suspend fun addLessonToGroup(lessonId: Long, groupId: Long)
    fun initial()
    suspend fun getAttendance(groupId: Long)
}

@ExperimentalCoroutinesApi
class GroupDetailsRepositoryImpl(
    private val groupLocalDataSource: GroupLocalDataSource,
    private val lessonLocalDataSource: LessonLocalDataSource,
    private val memberLocalDataSource: MemberLocalDataSource
) : GroupDetailsRepository {
    private val modelStateFlow: MutableStateFlow<GroupDetailsModelState> =
        MutableStateFlow(GroupDetailsModelState.Loading)

    override fun modelStateFlow(): Flow<GroupDetailsModelState> = modelStateFlow

    override suspend fun getGroupDetails(groupId: Long) {
        modelStateFlow.value = GroupDetailsModelState.Loading
        val groupDetails = groupLocalDataSource.getGroupDetails(groupId)
        modelStateFlow.value =
            if (groupDetails == null) {
                GroupDetailsModelState.DetailsLoadingError(Exception("Error"))
            } else {
                GroupDetailsModelState.LoadedDetails(groupDetails)
            }
    }

    override fun openFabOptions() {
        modelStateFlow.value = GroupDetailsModelState.FabClick
    }

    override fun dismissDialog() {
        modelStateFlow.value = GroupDetailsModelState.DialogDismiss
    }

    override suspend fun getLessonsToAdd(groupId: Long) {
        modelStateFlow.value = GroupDetailsModelState.LessonsLoading
        modelStateFlow.value = GroupDetailsModelState.LessonsLoaded(
            lessonLocalDataSource.getGroupLessonsToAdd(groupId)
        )
    }

    override suspend fun getMembersToAdd(groupId: Long) {
        modelStateFlow.value = GroupDetailsModelState.MembersLoading
        modelStateFlow.value = GroupDetailsModelState.MembersLoaded(
            memberLocalDataSource.getGroupMembersToAdd(groupId)
        )
    }

    override suspend fun addMemberToGroup(memberId: Long, groupId: Long) {
        groupLocalDataSource.addMemberToGroup(groupId, memberId)
    }

    override suspend fun addLessonToGroup(lessonId: Long, groupId: Long) {
        groupLocalDataSource.addLessonToGroup(groupId, lessonId)
    }

    override fun initial() {
        modelStateFlow.value = GroupDetailsModelState.Loading
    }

    override suspend fun getAttendance(groupId: Long) {
        val groupAttendance = groupLocalDataSource.getAttendance(groupId)
        modelStateFlow.value = GroupDetailsModelState.AttendanceLoaded(groupAttendance)
    }

}