package com.gymondo.presentaion.mapper

import com.gymondo.app.domain.dto.SearchResponse
import com.gymondo.presentaion.model.SearchResponseView


open class SearchResponseMapper(private val repositoryMapper: RepositoryMapper) :
    Mapper<SearchResponseView, SearchResponse> {

    override fun mapToView(domain: SearchResponse): SearchResponseView {
        return SearchResponseView(
            domain.totalCount, domain.repositories.map { repositoryMapper.mapToView(it) }
        )
    }

    override fun mapFromView(view: SearchResponseView): SearchResponse {
        return SearchResponse(
            view.totalCount ?: 0, view.repositories.map { repositoryMapper.mapFromView(it) }
        )
    }
}