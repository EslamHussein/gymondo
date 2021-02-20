package com.gymondo.app.remote.mapper

import com.gymondo.app.remote.dto.SearchResponseModel
import com.gymondo.data.model.SearchResponseEntity

class SearchResponseMapper(private val repositoryMapper: RepositoryMapper) :
    ModelMapper<SearchResponseModel, SearchResponseEntity> {
    override fun mapFromModel(model: SearchResponseModel): SearchResponseEntity {
        return SearchResponseEntity(
            model.totalCount,
            model.repositories.map { repositoryMapper.mapFromModel(it) })
    }
}