package com.mateus.batista.data_cache.mapper

interface CacheMapper<E, M> {
    fun toData(entity: E) : M
    fun fromData(model: M) : E
}