package com.leeloo.esist.cache.utils

import com.leeloo.esist.cache.entities.GroupEntity
import com.leeloo.esist.cache.entities.MemberEntity
import com.leeloo.esist.cache.vo.RoomGroupDetails
import com.leeloo.esist.cache.vo.RoomLesson
import com.leeloo.esist.cache.vo.RoomLessonDetails
import com.leeloo.esist.cache.vo.RoomMemberDetails
import com.leeloo.esist.model.*
import java.net.URI

fun MemberEntity.toMember(): Member = Member(
    memberId = memberId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    memberColor = memberColor
)

fun GroupEntity.toGroup(): Group = Group(
    groupId = groupId,
    groupName = groupName,
    groupColor = groupColor
)

fun RoomLesson.toLesson(): Lesson = Lesson(
    lessonId = this.lesson.lessonId,
    lessonSubject = this.lesson.subjectName,
    lessonColor = this.lesson.lessonColor,
    lessonTeacher = this.teacher.toMember(),
    startTimestamp = this.lesson.startTime,
    endTimestamp = this.lesson.finishTime
)

fun RoomMemberDetails.toMemberDetails(): MemberDetails = MemberDetails(
    memberId = this.member.memberId,
    firstName = this.member.firstName,
    middleName = this.member.middleName,
    lastName = this.member.lastName,
    memberGroups = this.groups.map { it.group.toGroup() },
    memberSchedule = this.groups.flatMap { it.lessons }.map { it.toLesson() },
    memberColor = this.member.memberColor
)

fun RoomGroupDetails.toGroupDetails(): GroupDetails = GroupDetails(
    groupId = this.group.groupId,
    groupName = this.group.groupName,
    groupColor = this.group.groupColor,
    groupMembers = this.members.map { it.toMember() },
    groupSchedule = this.lessons.map { it.toLesson() }
)


fun RoomLessonDetails.toLessonDetails(): LessonDetails = LessonDetails(
    lessonId = this.lesson.lessonId,
    lessonSubject = this.lesson.subjectName,
    lessonTopic = this.lesson.topicName,
    lessonHomework = this.lesson.homework,
    lessonColor = this.lesson.lessonColor,
    lessonTeacher = this.teacher.toMember(),
    startTimestamp = this.lesson.startTime,
    endTimestamp = this.lesson.finishTime,
    lessonBooks = this.books.map { URI.create(it.bookUri) },
    lessonGroups = this.groups.map { it.group.toGroup() }
)