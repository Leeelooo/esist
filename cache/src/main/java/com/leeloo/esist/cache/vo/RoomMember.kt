package com.leeloo.esist.cache.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.leeloo.esist.cache.entities.GroupMemberCrossRef
import com.leeloo.esist.cache.entities.MemberEntity

data class RoomMember(
    @Embedded val member: MemberEntity,
    @Relation(
        parentColumn = "member_id",
        entityColumn = "group_id",
        associateBy = Junction(GroupMemberCrossRef::class)
    )
    val groups: List<RoomGroup>
)