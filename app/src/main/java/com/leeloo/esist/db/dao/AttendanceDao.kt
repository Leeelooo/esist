package com.leeloo.esist.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.leeloo.esist.db.entity.AttendanceEntity

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttendance(attendance: AttendanceEntity): Long

    @Delete
    fun removeAttendance(attendance: AttendanceEntity): Int

}