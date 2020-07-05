package com.leeloo.esist.di

import android.content.Context
import androidx.room.Room
import com.leeloo.esist.db.EsistDatabase
import com.leeloo.esist.db.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EsistDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            EsistDatabase::class.java,
            "esist.db"
        ).build()

    @Singleton
    @Provides
    fun provideAttendanceDao(db: EsistDatabase): AttendanceDao = db.attendanceDao()

    @Singleton
    @Provides
    fun provideBookDao(db: EsistDatabase): BookDao = db.bookDao()

    @Singleton
    @Provides
    fun provideCrossRefDao(db: EsistDatabase): CrossRefDao = db.crossRefDao()

    @Singleton
    @Provides
    fun provideGroupDao(db: EsistDatabase): GroupDao = db.groupDao()

    @Singleton
    @Provides
    fun provideLessonDao(db: EsistDatabase): LessonDao = db.lessonDao()

    @Singleton
    @Provides
    fun provideMemberDao(db: EsistDatabase): MemberDao = db.memberDao()

}