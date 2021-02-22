package com.gymondo.data.test.factory

import com.gymondo.app.domain.dto.Owner
import com.gymondo.app.domain.dto.Repository
import com.gymondo.data.model.OwnerEntity
import com.gymondo.data.model.RepositoryEntity
import java.util.*
import java.util.concurrent.ThreadLocalRandom


fun makeRepositoryEntity(): RepositoryEntity = RepositoryEntity(
    randomInt(), randomString(),
    makeOwnerEntity(), randomString(), randomInt(),
    randomInt(), randomString(), randomInt(),
    randomString()
)

fun makeRepository(): Repository = Repository(
    randomInt(), randomString(),
    makeOwner(), randomString(), randomInt(),
    randomInt(), randomString(), randomInt(),
    randomString()
)


fun makeOwner(): Owner = Owner(randomString(), randomInt(), randomString())
fun makeOwnerEntity(): OwnerEntity = OwnerEntity(randomString(), randomInt(), randomString())

fun randomString(): String = UUID.randomUUID().toString()


fun randomInt(): Int {
    return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
}
