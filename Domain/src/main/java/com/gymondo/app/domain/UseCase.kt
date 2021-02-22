package com.gymondo.app.domain

interface UseCase<P, R> {
    suspend fun execute(params: P): R
}