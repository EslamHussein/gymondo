package com.gymondo.data.mapper

import com.gymondo.app.domain.dto.Repository
import com.gymondo.data.model.RepositoryEntity

open class RepositoryMapper(private val ownerMapper: OwnerMapper) :
    EntityMapper<RepositoryEntity, Repository> {
    override fun mapFromEntity(entity: RepositoryEntity): Repository {
        return Repository(
            entity.id,
            entity.name,
            ownerMapper.mapFromEntity(entity.owner),
            entity.description,
            entity.watchersCount,
            entity.stargazersCount,
            entity.language,
            entity.forksCount,
            entity.url
        )
    }

    override fun mapToEntity(data: Repository): RepositoryEntity {
        return RepositoryEntity(
            data.id,
            data.name,
            ownerMapper.mapToEntity(data.owner),
            data.description,
            data.watchersCount,
            data.stargazersCount,
            data.language,
            data.forksCount,
            data.url
        )
    }


}