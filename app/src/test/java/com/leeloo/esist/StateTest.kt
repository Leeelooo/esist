package com.leeloo.esist

import com.leeloo.esist.group.list.GroupModelState
import com.leeloo.esist.group.list.GroupViewState
import com.leeloo.esist.member.details.MemberDetailsModelState
import com.leeloo.esist.member.details.MemberDetailsViewState
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.Lesson
import org.junit.Test

class StateTest {

    @Test
    fun reducingListStateToInsertionState() {
        val groupViewState = GroupViewState.groupInserted(
            listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.DialogOpen(
            lessons = listOf(
                Lesson(
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            members = emptyList()
        )
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = emptyList(),
            lessonsToAdd = listOf(
                Lesson(
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }

    @Test
    fun errorInsertingDetailsState() {
        val exception = Exception("test")
        val oldState = MemberDetailsViewState.initialLoading
        val modelState = MemberDetailsModelState.MemberDetailsLoadingError(exception)
        val newState = modelState.reduce(oldState)
        val stateToCompare = MemberDetailsViewState(
            loadingMemberDetails = false,
            isDialogOpen = false,
            pastLessons = emptyList(),
            visitedLessons = emptyList(),
            memberDetailsLoadingError = exception,
            memberDetails = null
        )
        assert(stateToCompare == newState)
    }

    @Test
    fun reducingDialogStateToDismissState() {
        val groupViewState = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = emptyList(),
            lessonsToAdd = listOf(
                Lesson(
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.DialogDismiss
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = emptyList(),
            lessonsToAdd = emptyList(),
            membersToAdd = emptyList(),
            isDialogOpened = false,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }

    @Test
    fun reducingDialogStateToSelectedGroupWithEmptySelection() {
        val groupViewState = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = emptyList(),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.LessonChecked(1L)
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }

    @Test
    fun reducingDialogStateToSelectedGroupWithSelectionOfThisType() {
        val groupViewState = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.LessonChecked(2L)
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L, 2L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }

    @Test
    fun reducingDialogStateToUnselectGroup() {
        val groupViewState = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L, 2L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.LessonChecked(2L)
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }


    @Test
    fun reducingDialogStateToSelectedGroupWithSelectionOfOtherType() {
        val groupViewState = GroupViewState(
            loading = false,
            selectedMembers = emptyList(),
            selectedLessons = listOf(1L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        val groupModelState = GroupModelState.MemberChecked(3L)
        val newState = groupModelState.reduce(groupViewState)
        val stateToCompare = GroupViewState(
            loading = false,
            selectedMembers = listOf(   3L),
            selectedLessons = listOf(1L),
            lessonsToAdd = listOf(
                Lesson(
                    lessonId = 1L,
                    lessonSubject = "english",
                    lessonTopic = "english",
                    startTimestamp = 1L,
                    endTimestamp = 2L,
                    lessonColor = 1,
                    book = null,
                    homework = null
                )
            ),
            membersToAdd = emptyList(),
            isDialogOpened = true,
            groupInsertionError = null,
            isGroupInserted = false,
            error = null,
            groups = listOf(
                Group(groupName = "1", groupColor = 1),
                Group(groupName = "2", groupColor = 2)
            )
        )
        assert(stateToCompare == newState)
    }

}