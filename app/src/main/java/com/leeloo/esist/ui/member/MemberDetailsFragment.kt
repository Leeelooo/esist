package com.leeloo.esist.ui.member

import androidx.fragment.app.viewModels
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.member.details.MemberDetailsAction
import com.leeloo.esist.member.details.MemberDetailsIntent
import com.leeloo.esist.member.details.MemberDetailsViewModel
import com.leeloo.esist.member.details.MemberDetailsViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MemberDetailsFragment :
    BaseFragment<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>() {
    private val _viewModel: MemberDetailsViewModel by viewModels()
//    private val _intents: MutableStateFlow<MemberDetailsIntent> =
//        MutableStateFlow(MemberDetailsIntent.LoadMemberDetailsIntent())

    override val viewModel: BaseViewModel<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_member_details
    override val intentsFlow: Flow<MemberDetailsIntent>
        get() = TODO()

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun render(viewState: MemberDetailsViewState) {
        TODO("Not yet implemented")
    }
}