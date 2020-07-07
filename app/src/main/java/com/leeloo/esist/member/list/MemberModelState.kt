package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Group
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

    class DialogOpen(
        private val groups: List<Group>
    ) : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.openDialog(oldState.members, groups)
    }

    class GroupSelected(
        private val groupId: Long
    ) : MemberModelState() {
        override fun reduce(oldState: MemberViewState): MemberViewState =
            MemberViewState.groupClicked(
                oldState.members,
                oldState.groupsToAdd,
                oldState.selectedGroups.toMutableSet().apply {
                    if (contains(groupId)) {
                        remove(groupId)
                    } else {
                        add(groupId)
                    }
                }.toList()
            )
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
            MemberViewState.lessonInsertionError(
                oldState.members,
                oldState.groupsToAdd,
                oldState.selectedGroups,
                error
            )
    }

}
