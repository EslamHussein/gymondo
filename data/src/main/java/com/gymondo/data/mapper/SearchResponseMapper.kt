package com.gymondo.data.mapper

import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.data.model.SearchResponseEntity

class SearchResponseMapper(private val repositoryMapper: RepositoryMapper) :
    EntityMapper<SearchResponseEntity, SearchResponse> {
    override fun mapFromEntity(entity: SearchResponseEntity): SearchResponse {
        return SearchResponse(
            entity.totalCount,
            entity.repositories.map { repositoryMapper.mapFromEntity(it) })
    }

    override fun mapToEntity(data: SearchResponse): SearchResponseEntity {
        return SearchResponseEntity(
            data.totalCount,
            data.repositories.map { repositoryMapper.mapToEntity(it) })
    }
}