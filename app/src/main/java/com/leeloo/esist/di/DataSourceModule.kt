package com.leeloo.esist.di

import com.leeloo.esist.db.dao.*
import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.group.GroupLocalDataSourceImpl
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.lesson.LessonLocalDataSourceImpl
import com.leeloo.esist.member.MemberLocalDataSource
import com.leeloo.esist.member.MemberLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideGroupLocalDataSource(
        groupDao: GroupDao,
        lessonDao: LessonDao,
        memberDao: MemberDao,
        crossRefDao: CrossRefDao
    ): GroupLocalDataSource =
        GroupLocalDataSourceImpl(
            groupDao = groupDao,
            lessonDao = lessonDao,
            memberDao = memberDao,
            crossRefDao = crossRefDao
        )

    @Singleton
    @Provides
    fun provideLessonLocalDataSource(
        lessonDao: LessonDao,
        groupDao: GroupDao,
        attendanceDao: AttendanceDao,
        crossRefDao: CrossRefDao,
        memberDao: MemberDao
    ): LessonLocalDataSource =
        LessonLocalDataSourceImpl(
            lessonDao = lessonDao,
            groupDao = groupDao,
            attendanceDao = attendanceDao,
            crossRefDao = crossRefDao,
            memberDao = memberDao
        )

    @Singleton
    @Provides
    fun provideMemberLocalDataSource(
        memberDao: MemberDao,
        groupDao: GroupDao,
        lessonDao: LessonDao,
        crossRefDao: CrossRefDao
    ): MemberLocalDataSource = MemberLocalDataSourceImpl(
        memberDao = memberDao,
        groupDao = groupDao,
        lessonDao = lessonDao,
        crossRefDao = crossRefDao
    )

}