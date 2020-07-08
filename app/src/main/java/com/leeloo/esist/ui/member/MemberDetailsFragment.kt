package com.leeloo.esist.ui.member

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.member.details.MemberDetailsAction
import com.leeloo.esist.member.details.MemberDetailsIntent
import com.leeloo.esist.member.details.MemberDetailsViewModel
import com.leeloo.esist.member.details.MemberDetailsViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.adapters.MemberGroupsAdapter
import com.leeloo.esist.ui.recycler.adapters.MemberLessonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_member_details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@AndroidEntryPoint
class MemberDetailsFragment :
    BaseFragment<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>() {
    private lateinit var coordinator: Coordinator
    private val _viewModel: MemberDetailsViewModel by viewModels()
    private val _intents: MutableStateFlow<MemberDetailsIntent> =
        MutableStateFlow(MemberDetailsIntent.InitialIntent)

    private lateinit var groupAdapter: MemberGroupsAdapter
    private lateinit var lessonAdapter: MemberLessonAdapter

    private var isJumpedToPresent = false
    private var memberId: Long = 0L

    override val viewModel: BaseViewModel<MemberDetailsViewState, MemberDetailsIntent, MemberDetailsAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_member_details
    override val intentsFlow: Flow<MemberDetailsIntent>
        get() = _intents

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            _intents.value = MemberDetailsIntent.LoadMemberDetailsIntent(memberId)
        }
    }

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        if (arguments != null) {
            memberId = arguments?.getLong("MEMBER_ID") ?: 0L
        }
        back_arrow.setOnClickListener { coordinator.popBackStack() }
        member_statistic.setOnClickListener {
            _intents.value = MemberDetailsIntent.GetMemberStatisticIntent(memberId)
        }
        member_email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, member_email.text)
            startActivity(Intent.createChooser(intent, resources.getString(R.string.send_email)))
        }
        groupAdapter = MemberGroupsAdapter { coordinator.navigateToGroupDetails(it) }
        lessonAdapter = MemberLessonAdapter { coordinator.navigateToLessonDetails(it) }
        member_groups.adapter = groupAdapter
        member_groups.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        member_schedule.adapter = lessonAdapter
        member_schedule.layoutManager = LinearLayoutManager(context)
    }

    override fun render(viewState: MemberDetailsViewState) {
        if (viewState.memberDetails != null && view != null) {
            member_name.text =
                "${viewState.memberDetails.firstName} ${viewState.memberDetails.lastName}"
            member_email.text = viewState.memberDetails.emailAddress
            groupAdapter.updateData(viewState.memberDetails.memberGroups)
            lessonAdapter.updateData(viewState.memberDetails.memberSchedule)
            member_icon.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_member_circle
                )
            )
            DrawableCompat.setTint(
                member_icon.drawable,
                ContextCompat.getColor(requireContext(), viewState.memberDetails.memberColor)
            )
            if (viewState.memberDetails.memberSchedule.isNotEmpty() && !isJumpedToPresent) {
                try {
                    val upcomingLesson = viewState.memberDetails.memberSchedule.first {
                        System.currentTimeMillis() < it.endTimestamp
                    }
                    recycler.layoutManager?.scrollToPosition(
                        viewState.memberDetails.memberSchedule.indexOf(
                            upcomingLesson
                        )
                    )
                    isJumpedToPresent = true
                } catch (e: Exception) {

                }
            }
        }
    }
}