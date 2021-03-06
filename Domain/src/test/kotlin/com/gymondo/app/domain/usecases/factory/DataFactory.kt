package com.gymondo.app.domain.usecases.factory

import com.gymondo.app.domain.dto.Owner
import com.gymondo.app.domain.dto.Repository
import java.util.*
import java.util.concurrent.ThreadLocalRandom


fun makeRepository(): Repository = Repository(
    randomInt(), randomString(),
    makeOwner(), randomString(), randomInt(),
    randomInt(), randomString(), randomInt(),
    randomString()
)


fun makeOwner(): Owner = Owner(randomString(), randomInt(), randomString())

fun randomString(): String = UUID.randomUUID().toString()


fun randomInt(): Int {
    return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
}
