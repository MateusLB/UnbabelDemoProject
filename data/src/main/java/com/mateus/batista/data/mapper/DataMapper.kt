package com.mateus.batista.data.mapper

interface DataMapper<E, D> {
    fun toDomain(data: E): D
    fun toData(data: D): E
}