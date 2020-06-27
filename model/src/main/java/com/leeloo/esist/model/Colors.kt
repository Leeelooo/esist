package com.leeloo.esist.model

/**
 * Typealias of Color Resource for members(@see[Member])
 * Instead of runtime random assigning of color, storing int value of resource to prevent ambiguity
 * after lifecycle changes.
 */
typealias MemberColor = Int

/**
 * Typealias of Color Resource for groups(@see[Group])
 * Instead of runtime random assigning of color, storing int value of resource to prevent ambiguity
 * after lifecycle changes.
 */
typealias GroupColor = Int

/**
 * Typealias of Color Resource for lessons(@see[Lesson])
 * Instead of runtime random assigning of color, storing int value of resource to prevent ambiguity
 * after lifecycle changes.
 */
typealias LessonColor = Int