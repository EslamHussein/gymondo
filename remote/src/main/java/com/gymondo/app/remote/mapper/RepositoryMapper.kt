package com.gymondo.app.remote.mapper

import com.gymondo.app.remote.dto.RepositoryModel
import com.gymondo.data.model.RepositoryEntity

class RepositoryMapper(private val ownerMapper: OwnerMapper) :
    ModelMapper<RepositoryModel, RepositoryEntity> {
    override fun mapFromModel(model: RepositoryModel): RepositoryEntity {

        return RepositoryEntity(
            model.id,
            model.name,
            ownerMapper.mapFromModel(model.owner),
            model.description,
            model.watchersCount,
            model.stargazersCount,
            model.language,
            model.forksCount,
            model.url
        )
    }
}