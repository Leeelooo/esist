package com.leeloo.esist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey @ColumnInfo(name = "book_id") val bookId: Long,
    @ColumnInfo(name = "book_uri") val bookUri: String
)
