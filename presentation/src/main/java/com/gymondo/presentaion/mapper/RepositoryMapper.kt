package com.gymondo.presentaion.mapper

import com.gymondo.app.domain.dto.Repository
import com.gymondo.presentaion.model.RepositoryView

open class RepositoryMapper(private val ownerMapper: OwnerMapper) :
    Mapper<RepositoryView, Repository> {
    override fun mapToView(domain: Repository): RepositoryView {


        return RepositoryView(
            domain.id,
            domain.name,
            ownerMapper.mapToView(domain.owner),
            domain.description,
            domain.watchersCount,
            domain.stargazersCount,
            domain.language,
            domain.forksCount,
            domain.url
        )

    }

    override fun mapFromView(view: RepositoryView): Repository {
        return Repository(
            view.id,
            view.name,
            ownerMapper.mapFromView(view.owner),
            view.description,
            view.watchersCount,
            view.stargazersCount,
            view.language,
            view.forksCount,
            view.url
        )
    }
}

