package com.leeloo.esist.ui.lesson

import android.text.format.DateUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.lesson.details.LessonDetailsAction
import com.leeloo.esist.lesson.details.LessonDetailsIntent
import com.leeloo.esist.lesson.details.LessonDetailsViewModel
import com.leeloo.esist.lesson.details.LessonDetailsViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.adapters.LessonAttendanceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_member_details.back_arrow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class LessonDetailsFragment :
    BaseFragment<LessonDetailsViewState, LessonDetailsIntent, LessonDetailsAction>() {
    private val _viewModel: LessonDetailsViewModel by viewModels()
    private val _intents: MutableStateFlow<LessonDetailsIntent> =
        MutableStateFlow(LessonDetailsIntent.InitialIntent)

    private lateinit var coordinator: Coordinator
    private lateinit var adapter: LessonAttendanceAdapter
    private var lessonId: Long = 0L

    override val viewModel: BaseViewModel<LessonDetailsViewState, LessonDetailsIntent, LessonDetailsAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_lesson_details
    override val intentsFlow: Flow<LessonDetailsIntent>
        get() = _intents

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        if (arguments != null) {
            lessonId = arguments?.getLong("LESSON_ID") ?: 0L
            _intents.value = LessonDetailsIntent.LoadLessonDetailsIntent(lessonId)
        }
        back_arrow.setOnClickListener { coordinator.popBackStack() }
        lesson_statistic.setOnClickListener {

        }
        adapter = LessonAttendanceAdapter(
            onClick = { coordinator.navigateToMemberDetails(it) },
            onSetAttendance = {
                _intents.value = LessonDetailsIntent.SetAttendanceToMemberIntent(lessonId, it)
            },
            onRemoveAttendance = {
                _intents.value = LessonDetailsIntent.RemoveAttendanceToMemberIntent(lessonId, it)
            }
        )
        lesson_attendance.adapter = adapter
        lesson_attendance.layoutManager = LinearLayoutManager(requireContext())
        lesson_attendance.addItemDecoration(
            DividerItemDecoration(
                lesson_attendance.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun render(viewState: LessonDetailsViewState) {
        if (viewState.lessonDetails != null && view != null) {
            lesson_date.text = DateUtils.formatDateTime(
                context,
                viewState.lessonDetails.startTimestamp,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
            )
            lesson_time.text = "${DateUtils.formatDateTime(
                context,
                viewState.lessonDetails.startTimestamp,
                DateUtils.FORMAT_SHOW_TIME
            )} - ${DateUtils.formatDateTime(
                context,
                viewState.lessonDetails.endTimestamp,
                DateUtils.FORMAT_SHOW_TIME
            )}"
            lesson_subject.text = viewState.lessonDetails.lessonSubject
            lesson_topic.text = viewState.lessonDetails.lessonTopic
            if (viewState.lessonDetails.lessonBook == null || viewState.lessonDetails.lessonBook.isEmpty()) {
                lesson_book.visibility = View.GONE
                lesson_book_title.visibility = View.GONE
            } else {
                lesson_book.visibility = View.VISIBLE
                lesson_book_title.visibility = View.VISIBLE
                lesson_book.text = viewState.lessonDetails.lessonBook
            }
            if (viewState.lessonDetails.lessonHomework == null || viewState.lessonDetails.lessonHomework.isEmpty()) {
                lesson_homework.visibility = View.GONE
                lesson_homework_title.visibility = View.GONE
            } else {
                lesson_homework.visibility = View.VISIBLE
                lesson_homework_title.visibility = View.VISIBLE
                lesson_homework.text = viewState.lessonDetails.lessonHomework
            }
            adapter.addData(
                viewState.lessonDetails.lessonMembers,
                viewState.lessonDetails.checkedMembers
            )
        }
    }

}