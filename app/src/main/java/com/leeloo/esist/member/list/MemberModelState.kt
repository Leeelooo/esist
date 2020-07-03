package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Member

sealed class MemberModelState : BaseModelState<MemberViewState> {

    object Initial : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState = MemberViewState.initial
    }

    class ErrorInitialLoading(
        private val error: Throwable
    ) : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.loadingError(error)
    }

    class MembersLoaded(
        private val members: List<Member>
    ) : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.loadedLessons(members)
    }

    object DialogOpen : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.openDialog(oldState.members)
    }

    object DialogDismiss : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.loadedLessons(oldState.members)
    }

    object MemberInserted : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.lessonInserted(oldState.members)
    }

    class MemberInsertionError(
        private val error: Throwable
    ) : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.lessonInsertionError(oldState.members, error)
    }

}
