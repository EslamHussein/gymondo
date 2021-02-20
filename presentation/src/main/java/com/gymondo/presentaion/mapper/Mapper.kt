package com.gymondo.presentaion.mapper

interface Mapper<V, D> {
    fun mapToView(domain: D): V
    fun mapFromView(view: V): D
}