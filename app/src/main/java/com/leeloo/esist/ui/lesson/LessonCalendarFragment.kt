package com.leeloo.esist.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.leeloo.esist.R
import com.leeloo.esist.db.dao.AttendanceDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LessonCalendarFragment : Fragment() {
    @Inject
    lateinit var attendanceDao: AttendanceDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_calendar,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar_view.date = System.currentTimeMillis()
        expected_count.text = "0"
        visits_count.text = "0"
        calendar_view.setOnDateChangeListener { calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
            lifecycleScope.launch {
                val lessonDateStart = GregorianCalendar(i, i1, i2).timeInMillis
                val lessonDateFinish = lessonDateStart + TimeUnit.DAYS.toMillis(1L)
                val getAllCount =
                    attendanceDao.getMembers(lessonDateStart, lessonDateFinish).toSet()
                val getVisitedCount =
                    attendanceDao.getVisitedMembers(lessonDateStart, lessonDateFinish)
                expected_count.text = getAllCount.size.toString()
                visits_count.text = getVisitedCount.size.toString()
            }
        }
    }

}