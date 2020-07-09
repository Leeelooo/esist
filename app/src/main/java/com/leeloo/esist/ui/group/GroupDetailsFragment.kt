package com.leeloo.esist.ui.group

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.group.details.GroupDetailsAction
import com.leeloo.esist.group.details.GroupDetailsIntent
import com.leeloo.esist.group.details.GroupDetailsViewModel
import com.leeloo.esist.group.details.GroupDetailsViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.adapters.LessonStateAdapter
import com.leeloo.esist.ui.recycler.adapters.MemberStateAdapter
import com.leeloo.esist.ui.recycler.decor.LessonItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_group_details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class GroupDetailsFragment :
    BaseFragment<GroupDetailsViewState, GroupDetailsIntent, GroupDetailsAction>() {
    private val _viewModel: GroupDetailsViewModel by viewModels()
    private val _intents: MutableStateFlow<GroupDetailsIntent> =
        MutableStateFlow(GroupDetailsIntent.InitialIntent)

    private lateinit var lessonAdapter: LessonStateAdapter
    private lateinit var memberAdapter: MemberStateAdapter
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    private lateinit var coordinator: Coordinator
    private var groupId: Long = 0L

    override val viewModel: BaseViewModel<GroupDetailsViewState, GroupDetailsIntent, GroupDetailsAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_group_details
    override val intentsFlow: Flow<GroupDetailsIntent>
        get() = _intents

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        if (arguments != null) {
            groupId = arguments?.getLong("GROUP_ID") ?: 0L
            _intents.value = GroupDetailsIntent.GroupDetailsLoading(groupId)
        }
        pagerAdapter = ScreenSlidePagerAdapter()
        viewpager.adapter = pagerAdapter
        lessonAdapter = LessonStateAdapter(
            { _intents.value = GroupDetailsIntent.ReloadLessonIntent(groupId) },
            { coordinator.navigateToLessonDetails(it) }
        )
        memberAdapter = MemberStateAdapter(
            { _intents.value = GroupDetailsIntent.ReloadMemberIntent(groupId) },
            { coordinator.navigateToMemberDetails(it) }
        )
        members_recycler.adapter = memberAdapter
        members_recycler.layoutManager = LinearLayoutManager(requireContext())
        lessons_recycler.adapter = lessonAdapter
        lessons_recycler.layoutManager = LinearLayoutManager(requireContext())
        lessons_recycler.addItemDecoration(LessonItemDecorator(resources.getDimensionPixelSize(R.dimen.margin_default)))
        members_recycler.addItemDecoration(
            DividerItemDecoration(
                members_recycler.context,
                DividerItemDecoration.VERTICAL
            )
        )
        back_arrow.setOnClickListener { coordinator.popBackStack() }
        group_statistic.setOnClickListener {
            _intents.value = GroupDetailsIntent.CheckStatisticIntent(groupId)
        }
//        add_fab.setOnClickListener {
//
//        }
    }

    override fun render(viewState: GroupDetailsViewState) {
        if (viewState.group != null && view != null) {
            group_title.text = viewState.group.groupName
            tabs.setupWithViewPager(viewpager)
            lessonAdapter.updateData(
                viewState.group.groupSchedule,
                isError = false,
                isLoading = false
            )
            memberAdapter.updateData(
                viewState.group.groupMembers,
                isError = false,
                isLoading = false
            )
            if (viewState.attendance != null) {
                Toast.makeText(
                    context,
                    viewState.attendance.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private inner class ScreenSlidePagerAdapter : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any =
            if (position == 0) {
                members_recycler
            } else {
                lessons_recycler
            }

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? =
            if (position == 0) {
                resources.getString(R.string.tab_member_title)
            } else {
                resources.getString(R.string.tab_lessons_title)
            }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        }
    }

}