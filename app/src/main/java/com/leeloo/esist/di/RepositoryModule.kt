package com.leeloo.esist.di

import com.leeloo.esist.group.GroupLocalDataSource
import com.leeloo.esist.group.details.GroupDetailsRepository
import com.leeloo.esist.group.details.GroupDetailsRepositoryImpl
import com.leeloo.esist.group.list.GroupRepository
import com.leeloo.esist.group.list.GroupRepositoryImpl
import com.leeloo.esist.lesson.LessonLocalDataSource
import com.leeloo.esist.lesson.details.LessonDetailsRepository
import com.leeloo.esist.lesson.details.LessonDetailsRepositoryImpl
import com.leeloo.esist.lesson.list.LessonRepository
import com.leeloo.esist.lesson.list.LessonRepositoryImpl
import com.leeloo.esist.member.MemberLocalDataSource
import com.leeloo.esist.member.details.MemberDetailsRepository
import com.leeloo.esist.member.details.MemberDetailsRepositoryImpl
import com.leeloo.esist.member.list.MemberRepository
import com.leeloo.esist.member.list.MemberRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideGroupDetailsRepository(
        groupLocalDataSource: GroupLocalDataSource,
        lessonLocalDataSource: LessonLocalDataSource,
        memberLocalDataSource: MemberLocalDataSource
    ): GroupDetailsRepository =
        GroupDetailsRepositoryImpl(
            groupLocalDataSource,
            lessonLocalDataSource,
            memberLocalDataSource
        )

    @Singleton
    @Provides
    fun provideGroupRepository(
        groupLocalDataSource: GroupLocalDataSource
    ): GroupRepository = GroupRepositoryImpl(groupLocalDataSource)

    @Singleton
    @Provides
    fun provideLessonDetailsRepository(
        lessonLocalDataSource: LessonLocalDataSource
    ): LessonDetailsRepository = LessonDetailsRepositoryImpl(lessonLocalDataSource)

    @Singleton
    @Provides
    fun provideLessonRepository(
        lessonLocalDataSource: LessonLocalDataSource
    ): LessonRepository = LessonRepositoryImpl(lessonLocalDataSource)

    @Singleton
    @Provides
    fun provideMemberDetailsRepository(
        memberLocalDataSource: MemberLocalDataSource
    ): MemberDetailsRepository = MemberDetailsRepositoryImpl(memberLocalDataSource)

    @Singleton
    @Provides
    fun provideMemberRepository(
        memberLocalDataSource: MemberLocalDataSource
    ): MemberRepository = MemberRepositoryImpl(memberLocalDataSource)

}