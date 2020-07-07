package com.leeloo.esist.ui.lesson

import androidx.fragment.app.viewModels
import com.leeloo.esist.R
import com.leeloo.esist.base.BaseFragment
import com.leeloo.esist.base.BaseViewModel
import com.leeloo.esist.lesson.list.LessonAction
import com.leeloo.esist.lesson.list.LessonIntent
import com.leeloo.esist.lesson.list.LessonViewModel
import com.leeloo.esist.lesson.list.LessonViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LessonCalendarFragment : BaseFragment<LessonViewState, LessonIntent, LessonAction>() {
    private val _viewModel: LessonViewModel by viewModels()
    private val _intents: MutableStateFlow<LessonIntent> =
        MutableStateFlow(LessonIntent.InitialIntent)

    override val viewModel: BaseViewModel<LessonViewState, LessonIntent, LessonAction>
        get() = _viewModel
    override val layoutResource: Int
        get() = R.layout.fragment_calendar
    override val intentsFlow: Flow<LessonIntent>
        get() = _intents

    override fun initViews() {
        calendar_view.date = System.currentTimeMillis()
    }

    override fun render(viewState: LessonViewState) {

    }

}