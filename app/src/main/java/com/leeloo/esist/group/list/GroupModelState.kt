package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseModelState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.Lesson
import com.leeloo.esist.vo.Member

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

    class DialogOpen(
        private val lessons: List<Lesson>,
        private val members: List<Member>
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.openDialog(oldState.groups, members, lessons)
    }

    class LessonChecked(
        private val lessonId: Long
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.checkedChip(
                oldState.groups,
                oldState.membersToAdd,
                oldState.lessonsToAdd,
                oldState.selectedLessons.toMutableSet().apply {
                    if (contains(lessonId)) {
                        remove(lessonId)
                    } else {
                        add(lessonId)
                    }
                }.toList(),
                oldState.selectedMembers
            )
    }

    class MemberChecked(
        private val memberId: Long
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.checkedChip(
                oldState.groups,
                oldState.membersToAdd,
                oldState.lessonsToAdd,
                oldState.selectedLessons,
                oldState.selectedMembers.toMutableSet().apply {
                    if (contains(memberId)) {
                        remove(memberId)
                    } else {
                        add(memberId)
                    }
                }.toList()
            )
    }

    object DialogDismiss : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.loadedGroups(oldState.groups)
    }

    object GroupInserted : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.groupInserted(oldState.groups)
    }

    class GroupInsertionError(
        private val error: Throwable
    ) : GroupModelState() {
        override fun reduce(oldState: GroupViewState): GroupViewState =
            GroupViewState.groupInsertionError(
                oldState.groups,
                oldState.membersToAdd,
                oldState.lessonsToAdd,
                oldState.selectedLessons,
                oldState.selectedMembers,
                error
            )
    }

}