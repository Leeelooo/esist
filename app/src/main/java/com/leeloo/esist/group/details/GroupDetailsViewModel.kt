package com.leeloo.esist.group.details

import com.leeloo.esist.base.BaseViewModel

interface GroupDetailsViewModel : BaseViewModel<GroupDetailsViewState, GroupDetailsIntent> {
    fun onStart()
    fun onReloadGroup()

    fun onFabClick()
    fun onAddLesson()
    fun onAddMember()
    fun onDismissDialog()
    fun onMemberChoose(memberId: Long)
    fun onLessonChoose(lessonId: Long)

    fun onMemberClicked(memberId: Long)
    fun onLessonClicked(lessonId: Long)
}