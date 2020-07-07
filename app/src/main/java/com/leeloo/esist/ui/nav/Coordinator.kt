package com.leeloo.esist.ui.nav

import androidx.fragment.app.FragmentManager
import com.leeloo.esist.R
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
            .replace(R.id.main_container, MemberDetailsFragment())
            .addToBackStack("MEMBER:$memberId")
            .commit()
    }

    override fun navigateToGroupDetails(groupId: Long) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, MemberDetailsFragment())
//            .addToBackStack("MEMBER:$groupId")
//            .commit()
    }

    override fun navigateToLessonDetails(lessonId: Long) {
        TODO("Not yet implemented")
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

}



