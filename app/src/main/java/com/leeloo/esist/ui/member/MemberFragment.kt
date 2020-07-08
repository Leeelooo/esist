package com.leeloo.esist.ui.member

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.member.list.MemberAction
import com.leeloo.esist.member.list.MemberIntent
import com.leeloo.esist.member.list.MemberViewModel
import com.leeloo.esist.member.list.MemberViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.AddAdapter
import com.leeloo.esist.ui.recycler.adapters.MemberStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_add_member.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MemberFragment : BaseFragment<MemberViewState, MemberIntent, MemberAction>() {
    private lateinit var coordinator: Coordinator
    private val _viewModel: MemberViewModel by viewModels()
    private val _intents: MutableStateFlow<MemberIntent> =
        MutableStateFlow(MemberIntent.InitialIntent)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var adapter: MemberStateAdapter
    private lateinit var addAdapter: AddAdapter

    override val viewModel: BaseViewModel<MemberViewState, MemberIntent, MemberAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_list
    override val intentsFlow: Flow<MemberIntent>
        get() = _intents

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        adapter = MemberStateAdapter(
            { _intents.value = MemberIntent.ReloadIntent },
            { coordinator.navigateToMemberDetails(it) }
        )
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.addItemDecoration(
            DividerItemDecoration(
                recycler.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler.adapter = adapter
        add_fab.setOnClickListener { _intents.value = MemberIntent.OpenDialogIntent }

        bottomSheetDialog = BottomSheetDialog(this.requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_add_member)
        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.setOnDismissListener { _intents.value = MemberIntent.DismissDialogIntent }
        bottomSheetDialog.add_member_btn.setOnClickListener {
            when {
                bottomSheetDialog.first_name_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.first_name_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                bottomSheetDialog.last_name_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.last_name_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                bottomSheetDialog.email_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.email_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                else -> {
                    _intents.value = MemberIntent.CreateMemberIntent(
                        firstName = bottomSheetDialog.first_name_input.text.toString(),
                        lastName = bottomSheetDialog.last_name_input.text.toString(),
                        emailAddress = bottomSheetDialog.email_input.text.toString()
                    )
                }
            }
        }
        addAdapter = AddAdapter { _intents.value = MemberIntent.OnGroupClick(it) }
        bottomSheetDialog.recycler_add_groups.adapter = addAdapter
        bottomSheetDialog.recycler_add_groups.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun render(viewState: MemberViewState) {
        adapter.updateData(viewState.members, viewState.error != null, viewState.loading)
        if (viewState.isDialogOpened) {
            bottomSheetDialog.show()
            addAdapter.addData(viewState.groupsToAdd.map { it.groupName to it.groupId })
        } else {
            addAdapter.addData(emptyList())
            bottomSheetDialog.dismiss()
            bottomSheetDialog.first_name_input.setText("")
            bottomSheetDialog.first_name_input.clearFocus()
            bottomSheetDialog.last_name_input.setText("")
            bottomSheetDialog.last_name_input.clearFocus()
            bottomSheetDialog.email_input.setText("")
            bottomSheetDialog.email_input.clearFocus()
        }
        if (viewState.memberInsertionError != null) {
            bottomSheetDialog.email_input.error =
                resources.getString(R.string.input_error_taken)
        }
    }

}