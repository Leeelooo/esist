package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseViewState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.Member

data class GroupViewState(
    val loading: Boolean,
    val groups: List<Group>,
    val error: Throwable?,
    val isDialogOpened: Boolean,
    val membersToAdd: List<Member>,
    val lessonsToAdd: List<Lesson>,
    val selectedMembers: List<Long>,
    val selectedLessons: List<Long>,
    val isGroupInserted: Boolean,
    val groupInsertionError: Throwable?
) : BaseViewState {

    companion object {
        val initial: GroupViewState = GroupViewState(
            loading = true,
            groups = emptyList(),
            error = null,
            isDialogOpened = false,
            membersToAdd = emptyList(),
            lessonsToAdd = emptyList(),
            selectedLessons = emptyList(),
            selectedMembers = emptyList(),
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun loadedGroups(groups: List<Group>): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            membersToAdd = emptyList(),
            lessonsToAdd = emptyList(),
            selectedLessons = emptyList(),
            selectedMembers = emptyList(),
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun loadingError(error: Throwable): GroupViewState = GroupViewState(
            loading = false,
            groups = emptyList(),
            error = error,
            membersToAdd = emptyList(),
            lessonsToAdd = emptyList(),
            selectedLessons = emptyList(),
            selectedMembers = emptyList(),
            isDialogOpened = false,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun openDialog(
            groups: List<Group>,
            membersToAdd: List<Member>,
            lessonsToAdd: List<Lesson>
        ): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            membersToAdd = membersToAdd,
            lessonsToAdd = lessonsToAdd,
            selectedLessons = emptyList(),
            selectedMembers = emptyList(),
            isDialogOpened = true,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun checkedChip(
            groups: List<Group>,
            membersToAdd: List<Member>,
            lessonsToAdd: List<Lesson>,
            selectedLessons: List<Long>,
            selectedMembers: List<Long>
        ): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            membersToAdd = membersToAdd,
            lessonsToAdd = lessonsToAdd,
            selectedLessons = selectedLessons,
            selectedMembers = selectedMembers,
            isDialogOpened = true,
            isGroupInserted = false,
            groupInsertionError = null
        )

        fun groupInserted(groups: List<Group>): GroupViewState = GroupViewState(
            loading = true,
            groups = groups,
            error = null,
            membersToAdd = emptyList(),
            lessonsToAdd = emptyList(),
            selectedLessons = emptyList(),
            selectedMembers = emptyList(),
            isDialogOpened = false,
            isGroupInserted = true,
            groupInsertionError = null
        )

        fun groupInsertionError(
            groups: List<Group>,
            membersToAdd: List<Member>,
            lessonsToAdd: List<Lesson>,
            selectedLessons: List<Long>,
            selectedMembers: List<Long>,
            groupInsertionError: Throwable
        ): GroupViewState = GroupViewState(
            loading = false,
            groups = groups,
            error = null,
            membersToAdd = membersToAdd,
            lessonsToAdd = lessonsToAdd,
            selectedLessons = selectedLessons,
            selectedMembers = selectedMembers,
            isDialogOpened = true,
            isGroupInserted = false,
            groupInsertionError = groupInsertionError
        )

    }
}