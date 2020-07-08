package com.leeloo.esist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leeloo.esist.R
import com.leeloo.esist.ui.group.GroupFragment
import com.leeloo.esist.ui.lesson.LessonCalendarFragment
import com.leeloo.esist.ui.lesson.LessonFragment
import com.leeloo.esist.ui.member.MemberFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_main,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = bottom_navigation
        bottomNavigationView.setOnNavigationItemSelectedListener {
            LAST_ITEM = it.itemId
            when (it.itemId) {
                R.id.bottom_navigation_item_members -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_content, MemberFragment())
                        .commit()
                    true
                }
                R.id.bottom_navigation_item_groups -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_content, GroupFragment())
                        .commit()
                    true
                }
                R.id.bottom_navigation_item_lessons -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_content, LessonFragment())
                        .commit()
                    true
                }
                R.id.bottom_navigation_item_calendar -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_main_content, LessonCalendarFragment())
                        .commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
        bottomNavigationView.selectedItemId = LAST_ITEM
    }
    companion object {
        var LAST_ITEM: Int = R.id.bottom_navigation_item_members
    }
}