package com.leeloo.esist.ui.lesson

import android.text.format.DateUtils
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
import com.leeloo.esist.ui.recycler.AddAdapter
import com.leeloo.esist.ui.recycler.adapters.LessonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_add_lesson.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LessonFragment : BaseFragment<LessonViewState, LessonIntent, LessonAction>() {
    private val _viewModel: LessonViewModel by viewModels()
    private val _intents: MutableStateFlow<LessonIntent> =
        MutableStateFlow(LessonIntent.InitialIntent)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var adapter: LessonAdapter
    private lateinit var addAdapter: AddAdapter

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
        adapter = LessonAdapter(
            { _intents.value = LessonIntent.ReloadIntent },
            { view, lessonId -> }
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
                bottomSheetDialog.homework_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.homework_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                bottomSheetDialog.book_input.text?.isEmpty() != false -> {
                    bottomSheetDialog.book_input.error =
                        resources.getString(R.string.input_error_empty)
                }
                else -> {
                    _intents.value = LessonIntent.CreateGroupIntent(
                        subjectName = subject_name_input.text.toString(),
                        topicName = lesson_topic_input.text.toString(),
                        homework = homework_input.text.toString(),
                        book = book_input.text.toString(),
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
        bottomSheetDialog.bottom_add_date_btn.setOnClickListener {
            //TODO: picker
        }
        bottomSheetDialog.bottom_add_start_time_btn.setOnClickListener {
            //TODO: picker
        }
        bottomSheetDialog.bottom_add_finish_time_btn.setOnClickListener {
            //TODO: picker
        }
    }

    override fun render(viewState: LessonViewState) {
        adapter.updateData(viewState.lessons, viewState.error != null, viewState.loading)
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