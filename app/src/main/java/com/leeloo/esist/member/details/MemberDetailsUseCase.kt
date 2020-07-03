package com.leeloo.esist.member.details

import com.leeloo.esist.base.BaseUseCase

interface MemberDetailsUseCase : BaseUseCase<MemberDetailsModelState> {
    fun loadLessonDetails(lessonId: Long)
}