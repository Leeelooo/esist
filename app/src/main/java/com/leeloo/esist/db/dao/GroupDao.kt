package com.leeloo.esist.db.dao

import androidx.room.*
import com.leeloo.esist.vo.Group
import com.leeloo.esist.vo.GroupDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(group: Group): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroups(groups: List<Group>): List<Long>

    @Query(
        "SELECT * from Groups " +
                "WHERE group_name LIKE :phrase " +
                "ORDER BY group_color ASC"
    )
    fun getFilteredGroups(phrase: String): Flow<List<Group>>

    @Transaction
    @Query("SELECT * from Groups WHERE group_id = :groupId LIMIT 1")
    fun getGroupDetails(groupId: Long): Flow<GroupDetails>

}