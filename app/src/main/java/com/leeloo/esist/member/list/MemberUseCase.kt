package com.leeloo.esist.member.list

import com.leeloo.esist.base.BaseUseCase

interface MemberUseCase : BaseUseCase<MemberModelState> {
    fun getFilteredMembers(phrase: String)

    fun openDialog()
    fun dismissDialog()

    fun addMember(
        firstName: String,
        middleName: String?,
        lastName: String
    )
}