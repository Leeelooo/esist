package com.leeloo.esist.group.list

import com.leeloo.esist.base.BaseUseCase

interface GroupUseCase : BaseUseCase<GroupModelState> {
    fun getFilteredGroups(phrase: String)

    fun openDialog()
    fun dismissDialog()

    fun addGroup(groupName: String)
}