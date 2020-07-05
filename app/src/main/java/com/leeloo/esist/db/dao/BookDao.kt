package com.leeloo.esist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leeloo.esist.db.entity.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(books: List<BookEntity>): List<Long>

    @Query("SELECT * from Books")
    suspend fun getAllBooks(): List<BookEntity>

    @Query(
        "SELECT * from Books " +
                "INNER JOIN LessonBookCrossRef ON LessonBookCrossRef.book_id = Books.book_id " +
                "WHERE LessonBookCrossRef.lesson_id = :lessonId"
    )
    suspend fun getLessonBooks(lessonId: Long): List<BookEntity>

}