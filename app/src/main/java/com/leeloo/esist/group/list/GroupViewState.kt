package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Group

data class GroupViewState(
    val loading: Boolean,
    val groups: List<Group>,
    val error: Throwable?,
    val isDialogOpened: Boolean,
    val isGroupInserted: Boolean,
    val groupInsertionError: Throwable?
) : BaseViewState {

    companion object {
        val initial: GroupViewState = GroupViewState(
            loading = true,
            groups = emptyList(),
            error = null,
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun loadedGroups(groups: List<Group>): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun loadingError(error: Throwable): GroupViewState = GroupViewState(
            loading = false,
            groups = emptyList(),
            error = error,
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun openDialog(groups: List<Group>): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            isDialogOpened = true,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun groupInserted(groups: List<Group>): GroupViewState = GroupViewState(
            loading = true,
            groups = groups,
            error = null,
            isDialogOpened = false,
            isGroupInserted = true,
            groupInsertionError = null
        )

        fun groupInsertionError(
            groups: List<Group>,
            groupInsertionError: Throwable
        ): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = groupInsertionError
        )

    }
}