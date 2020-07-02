package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseUseCase
import com.leeloo.esist.group.list.GroupModelState

interface GroupDetailsUseCase : BaseUseCase<GroupModelState> {
    fun getGroupDetails(groupId: Long)

    fun openFabOptions()
    fun dismissDialog()

    fun getLessonsToAdd(groupId: Long)
    fun getMembersToAdd(groupId: Long)

    fun addMemberToGroup(memberId: Long, groupId: Long)
    fun addLessonToGroup(lessonId: Long, groupId: Long)
}