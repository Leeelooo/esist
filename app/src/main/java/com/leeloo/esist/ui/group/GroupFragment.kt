package com.leeloo.esist.ui.group

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.group.list.GroupAction
import com.leeloo.esist.group.list.GroupIntent
import com.leeloo.esist.group.list.GroupViewModel
import com.leeloo.esist.group.list.GroupViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.AddAdapter
import com.leeloo.esist.ui.recycler.adapters.GroupStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_add_group.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GroupFragment : BaseFragment<GroupViewState, GroupIntent, GroupAction>() {
    private lateinit var coordinator: Coordinator
    private val _viewModel: GroupViewModel by viewModels()
    private val _intents: MutableStateFlow<GroupIntent> =
        MutableStateFlow(GroupIntent.InitialIntent)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var adapter: GroupStateAdapter
    private lateinit var lessonAdapter: AddAdapter
    private lateinit var memberAdapter: AddAdapter

    override val viewModel: BaseViewModel<GroupViewState, GroupIntent, GroupAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_list
    override val intentsFlow: Flow<GroupIntent>
        get() = _intents

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        adapter = GroupStateAdapter(
            { _intents.value = GroupIntent.InitialIntent },
            { coordinator.navigateToGroupDetails(it) }
        )
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.addItemDecoration(
            DividerItemDecoration(
                recycler.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler.adapter = adapter
        add_fab.setOnClickListener { _intents.value = GroupIntent.OpenDialogIntent }

        bottomSheetDialog = BottomSheetDialog(this.requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_add_group)
        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.setOnDismissListener { _intents.value = GroupIntent.DismissDialogIntent }
        bottomSheetDialog.add_group_btn.setOnClickListener {
            when {
                bottomSheetDialog.group_name_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.group_name_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                else -> {
                    _intents.value = GroupIntent.CreateGroupIntent(
                        groupName = bottomSheetDialog.group_name_input.text.toString()
                    )
                }
            }
        }
        lessonAdapter = AddAdapter { _intents.value = GroupIntent.CheckLessonIntent(it) }
        bottomSheetDialog.recycler_add_lessons.adapter = lessonAdapter
        bottomSheetDialog.recycler_add_lessons.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        memberAdapter = AddAdapter { _intents.value = GroupIntent.CheckMemberIntent(it) }
        bottomSheetDialog.recycler_add_members.adapter = memberAdapter
        bottomSheetDialog.recycler_add_members.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun render(viewState: GroupViewState) {
        adapter.updateData(viewState.groups, viewState.error != null, viewState.loading)
        if (viewState.isDialogOpened) {
            bottomSheetDialog.show()
            lessonAdapter.addData(viewState.lessonsToAdd.map { it.lessonTopic to it.lessonId })
            memberAdapter.addData(viewState.membersToAdd
                .map { "${it.firstName} ${it.lastName}" to it.memberId })
        } else {
            lessonAdapter.addData(emptyList())
            memberAdapter.addData(emptyList())
            bottomSheetDialog.group_name_input.setText("")
            bottomSheetDialog.group_name_input.clearFocus()
            bottomSheetDialog.dismiss()
        }
        if (viewState.groupInsertionError != null) {
            bottomSheetDialog.group_name_input.error =
                resources.getString(R.string.input_error_taken)
        }
    }

}