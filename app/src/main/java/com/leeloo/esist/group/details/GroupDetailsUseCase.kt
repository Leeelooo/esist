package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

interface GroupDetailsUseCase : BaseUseCase {
    fun getModelStateFlow(groupId: Long): Flow<GroupDetailsModelState>

    suspend fun getLessonsToAdd(groupId: Long)
    suspend fun getMembersToAdd(groupId: Long)

    suspend fun addMemberToGroup(memberId: Long, groupId: Long)
    suspend fun addLessonToGroup(lessonId: Long, groupId: Long)
}