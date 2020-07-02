package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Group

sealed class GroupModelState : BaseModelState<GroupViewState> {

    object Initial : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState = GroupViewState.initial
    }

    class ErrorInitialLoading(
        private val error: Throwable
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.loadingError(error)
    }

    class GroupsLoaded(
        private val groups: List<Group>
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.loadedGroups(groups)
    }

    object DialogOpen : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.openDialog(oldState.groups)
    }

    object DialogDismiss : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.loadedGroups(oldState.groups)
    }

    object GroupInserted : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.groupInserted(oldState.groups)
    }

    class GroupInsetionError(
        private val error: Throwable
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.groupInsertionError(oldState.groups, error)
    }

}