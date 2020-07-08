package com.leeloo.esist.ui.lesson

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateUtils
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.lesson.list.LessonAction
import com.leeloo.esist.lesson.list.LessonIntent
import com.leeloo.esist.lesson.list.LessonViewModel
import com.leeloo.esist.lesson.list.LessonViewState
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import com.leeloo.esist.ui.recycler.AddAdapter
import com.leeloo.esist.ui.recycler.adapters.LessonStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_add_lesson.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LessonFragment : BaseFragment<LessonViewState, LessonIntent, LessonAction>() {
    private lateinit var coordinator: Coordinator
    private val _viewModel: LessonViewModel by viewModels()
    private val _intents: MutableStateFlow<LessonIntent> =
        MutableStateFlow(LessonIntent.InitialIntent)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var adapter: LessonStateAdapter
    private lateinit var addAdapter: AddAdapter

    private var isJumpedToPresent = false

    private var lessonDate: Long = 0L
    private var lessonStartTime: Long = 0L
    private var lessonEndTime: Long = 0L

    override val viewModel: BaseViewModel<LessonViewState, LessonIntent, LessonAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_list
    override val intentsFlow: Flow<LessonIntent>
        get() = _intents

    override fun initViews() {
        coordinator = CoordinatorImpl(requireActivity().supportFragmentManager)
        adapter = LessonStateAdapter(
            { _intents.value = LessonIntent.ReloadIntent },
            { coordinator.navigateToLessonDetails(it) }
        )
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.adapter = adapter
        add_fab.setOnClickListener { _intents.value = LessonIntent.OpenDialogIntent }

        bottomSheetDialog = BottomSheetDialog(this.requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_add_lesson)
        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.setOnDismissListener { _intents.value = LessonIntent.DismissDialogIntent }
        bottomSheetDialog.add_lesson_btn.setOnClickListener {
            when {
                bottomSheetDialog.subject_name_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.subject_name_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                bottomSheetDialog.lesson_topic_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.lesson_topic_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                else -> {
                    _intents.value = LessonIntent.CreateGroupIntent(
                        subjectName = bottomSheetDialog.subject_name_input.text.toString(),
                        topicName = bottomSheetDialog.lesson_topic_input.text.toString(),
                        homework = bottomSheetDialog.homework_input.text.toString(),
                        book = bottomSheetDialog.book_input.text.toString(),
                        startTime = lessonDate + lessonStartTime,
                        finishTime = lessonDate + lessonEndTime
                    )
                }
            }
        }
        addAdapter = AddAdapter { _intents.value = LessonIntent.GroupToAddSelectedIntent(it) }
        bottomSheetDialog.recycler_add.adapter = addAdapter
        bottomSheetDialog.recycler_add.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setTime()
        val dateDialog = DatePickerDialog(
            this.requireContext(),
            { _: DatePicker, i: Int, i1: Int, i2: Int ->
                lessonDate = GregorianCalendar(i, i1, i2).timeInMillis
                setTime()
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        val startTimeDialog = TimePickerDialog(
            this.requireContext(),
            { _, hour: Int, minute: Int ->
                lessonStartTime =
                    TimeUnit.HOURS.toMillis(hour.toLong()) + TimeUnit.MINUTES.toMillis(minute.toLong()) - TimeUnit.HOURS.toMillis(
                        3L
                    )
                setTime()
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        )
        val endTimeDialog = TimePickerDialog(
            this.requireContext(),
            { _, hour: Int, minute: Int ->
                lessonEndTime =
                    TimeUnit.HOURS.toMillis(hour.toLong()) + TimeUnit.MINUTES.toMillis(minute.toLong()) - TimeUnit.HOURS.toMillis(
                        3L
                    )
                setTime()
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        )

        bottomSheetDialog.bottom_add_date_btn.setOnClickListener {
            dateDialog.show()
            dateDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
            dateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
        }
        bottomSheetDialog.bottom_add_start_time_btn.setOnClickListener {
            startTimeDialog.show()
            startTimeDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
            startTimeDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
        }
        bottomSheetDialog.bottom_add_finish_time_btn.setOnClickListener {
            endTimeDialog.show()
            endTimeDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
            endTimeDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(this.requireContext(), R.color.colorAccent)
            )
        }
    }

    private fun setTime() {
        bottomSheetDialog.date_text.text = if (lessonDate == 0L) {
            resources.getString(R.string.bottom_add_time_na)
        } else {
            DateUtils.formatDateTime(
                view?.context,
                lessonDate,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
            )
        }
        bottomSheetDialog.start_time_text.text = if (lessonStartTime == 0L) {
            resources.getString(R.string.bottom_add_time_na)
        } else {
            DateUtils.formatDateTime(
                view?.context,
                lessonStartTime,
                DateUtils.FORMAT_SHOW_TIME
            )
        }
        bottomSheetDialog.finish_time_text.text = if (lessonEndTime == 0L) {
            resources.getString(R.string.bottom_add_time_na)
        } else {
            DateUtils.formatDateTime(
                view?.context,
                lessonEndTime,
                DateUtils.FORMAT_SHOW_TIME
            )
        }
    }

    override fun render(viewState: LessonViewState) {
        adapter.updateData(viewState.lessons, viewState.error != null, viewState.loading)
        if (viewState.lessons.isNotEmpty() && !isJumpedToPresent) {
            try {
                val upcomingLesson =
                    viewState.lessons.first { System.currentTimeMillis() < it.endTimestamp }
                recycler.layoutManager?.scrollToPosition(viewState.lessons.indexOf(upcomingLesson))
                isJumpedToPresent = true
            } catch (e: Exception) {

            }
        }
        if (viewState.isDialogOpened) {
            bottomSheetDialog.show()
            addAdapter.addData(viewState.groupToAdd.map { it.groupName to it.groupId })
        } else {
            bottomSheetDialog.subject_name_input.setText("")
            bottomSheetDialog.subject_name_input.clearFocus()
            bottomSheetDialog.lesson_topic_input.setText("")
            bottomSheetDialog.lesson_topic_input.clearFocus()
            bottomSheetDialog.homework_input.setText("")
            bottomSheetDialog.homework_input.clearFocus()
            bottomSheetDialog.book_input.setText("")
            bottomSheetDialog.book_input.clearFocus()
            addAdapter.addData(emptyList())
            bottomSheetDialog.dismiss()
        }
    }
}