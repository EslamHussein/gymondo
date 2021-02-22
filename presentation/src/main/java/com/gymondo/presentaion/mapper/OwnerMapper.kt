package com.gymondo.presentaion.mapper

import com.gymondo.app.domain.dto.Owner
import com.gymondo.presentaion.model.OwnerView

class OwnerMapper : Mapper<OwnerView, Owner> {
    override fun mapToView(domain: Owner): OwnerView {
        return OwnerView(domain.name, domain.id, domain.avatarUrl)
    }

    override fun mapFromView(view: OwnerView): Owner {
        return Owner(view.name, view.id, view.avatarUrl)
    }
}