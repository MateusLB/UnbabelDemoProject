package com.mateus.batista.data.mapper

interface DataMapper<M, D> {
    fun toDomain(data: M): D
    fun fromData(data: D): M
}