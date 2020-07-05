package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "book_id") var bookId: Long = 0L,
    @ColumnInfo(name = "book_uri") var bookUri: String = ""
)
