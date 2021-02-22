package com.gymondo.app.remote.mapper

interface ModelMapper<M, E> {
    fun mapFromModel(model: M): E
}