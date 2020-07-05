package com.leeloo.esist.ui.group

import androidx.fragment.app.viewModels
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseView
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.group.list.GroupAction
import com.leeloo.esist.group.list.GroupIntent
import com.leeloo.esist.group.list.GroupViewModel
import com.leeloo.esist.group.list.GroupViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GroupFragment : BaseView<GroupViewState, GroupIntent, GroupAction>() {
    private val _viewModel: GroupViewModel by viewModels()
    private val _intents: MutableStateFlow<GroupIntent> =
        MutableStateFlow(GroupIntent.InitialIntent)

    override val viewModel: BaseViewModel<GroupViewState, GroupIntent, GroupAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_list
    override val intentsFlow: Flow<GroupIntent>
        get() = _intents

    override fun initViews() {

    }

    override fun render(viewState: GroupViewState) {

    }

}