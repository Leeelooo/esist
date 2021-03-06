package com.leeloo.esist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leeloo.esist.db.entity.GroupMemberCrossRef
import com.leeloo.esist.db.entity.LessonGroupCrossRef

@Dao
interface CrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroupToMember(groupToMember: GroupMemberCrossRef): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLessonToGroup(lessonToGroup: LessonGroupCrossRef): Long

}