package com.gymondo.app.remote.mapper

import com.gymondo.app.remote.dto.OwnerModel
import com.gymondo.data.model.OwnerEntity

class OwnerMapper : ModelMapper<OwnerModel, OwnerEntity> {
    override fun mapFromModel(model: OwnerModel): OwnerEntity {
        return OwnerEntity(model.name, model.id, model.avatarUrl)
    }
}