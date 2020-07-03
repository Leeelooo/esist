package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Member

data class MemberViewState(
    val loading: Boolean,
    val members: List<Member>,
    val error: Throwable?,
    val isDialogOpened: Boolean,
    val isMemberInserted: Boolean,
    val memberInsertionError: Throwable?
) : BaseViewState {

    companion object {
        val initial: MemberViewState = MemberViewState(
            loading = true,
            members = emptyList(),
            error = null,
            isDialogOpened = false,
            isMemberInserted = false,
            memberInsertionError = null
        )

        fun loadedLessons(members: List<Member>): MemberViewState = MemberViewState(
            loading = false,
            members = members,
            error = null,
            isDialogOpened = false,
            isMemberInserted = false,
            memberInsertionError = null
        )

        fun loadingError(error: Throwable): MemberViewState = MemberViewState(
            loading = false,
            members = emptyList(),
            error = error,
            isDialogOpened = false,
            isMemberInserted = false,
            memberInsertionError = null
        )

        fun openDialog(members: List<Member>): MemberViewState = MemberViewState(
            loading = false,
            members = members,
            error = null,
            isDialogOpened = true,
            isMemberInserted = false,
            memberInsertionError = null
        )

        fun lessonInserted(members: List<Member>): MemberViewState = MemberViewState(
            loading = false,
            members = members,
            error = null,
            isDialogOpened = false,
            isMemberInserted = true,
            memberInsertionError = null
        )

        fun lessonInsertionError(
            members: List<Member>,
            lessonInsertionError: Throwable
        ): MemberViewState = MemberViewState(
            loading = false,
            members = members,
            error = null,
            isDialogOpened = false,
            isMemberInserted = false,
            memberInsertionError = lessonInsertionError
        )

    }
}