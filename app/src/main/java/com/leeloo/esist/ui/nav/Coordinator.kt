package com.leeloo.esist.ui.nav

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.leeloo.esist.R
import com.leeloo.esist.ui.group.GroupDetailsFragment
import com.leeloo.esist.ui.lesson.LessonDetailsFragment
import com.leeloo.esist.ui.member.MemberDetailsFragment

interface Coordinator {
    fun navigateToMemberDetails(memberId: Long)
    fun navigateToGroupDetails(groupId: Long)
    fun navigateToLessonDetails(lessonId: Long)
    fun popBackStack()
}

class CoordinatorImpl(
    private val supportFragmentManager: FragmentManager
) : Coordinator {

    override fun navigateToMemberDetails(memberId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MemberDetailsFragment().apply {
                arguments = Bundle().apply { putLong("MEMBER_ID", memberId) }
            })
            .addToBackStack("MEMBER:$memberId")
            .commit()
    }

    override fun navigateToGroupDetails(groupId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GroupDetailsFragment().apply {
                arguments = Bundle().apply { putLong("GROUP_ID", groupId) }
            })
            .addToBackStack("GROUP:$groupId")
            .commit()
    }

    override fun navigateToLessonDetails(lessonId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LessonDetailsFragment().apply {
                arguments = Bundle().apply { putLong("LESSON_ID", lessonId) }
            })
            .addToBackStack("LESSOND:$lessonId")
            .commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

}



