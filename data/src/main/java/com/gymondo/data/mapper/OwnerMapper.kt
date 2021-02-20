package com.gymondo.data.mapper

import com.gymondo.app.domain.dto.Owner
import com.gymondo.data.model.OwnerEntity

class OwnerMapper : EntityMapper<OwnerEntity, Owner> {
    override fun mapFromEntity(entity: OwnerEntity): Owner {
        return Owner(entity.name, entity.id, entity.avatarUrl)
    }

    override fun mapToEntity(data: Owner): OwnerEntity {
        return OwnerEntity(data.name, data.id, data.avatarUrl)
    }
}