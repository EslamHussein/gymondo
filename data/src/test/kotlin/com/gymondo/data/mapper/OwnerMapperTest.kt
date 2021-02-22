package com.gymondo.data.mapper

import com.gymondo.data.test.factory.makeOwner
import com.gymondo.data.test.factory.makeOwnerEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class OwnerMapperTest {
    private val mapper = OwnerMapper()

    @Test
    fun mapFromEntity() {

        val entity = makeOwnerEntity()
        val model = mapper.mapFromEntity(entity)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
    }

    @Test
    fun mapToEntity() {

        val model = makeOwner()
        val entity = mapper.mapToEntity(model)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)

    }
}