package com.mateus.batista.data_cache.mapper

import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data_cache.entity.UserEntity

object UserMapper : CacheMapper<UserEntity, UserModel> {

    override fun toData(entity: UserEntity): UserModel {
        return UserModel(
            id = entity.id,
            name = entity.name,
            username = entity.username,
            email = entity.email
        )
    }

    override fun fromData(model: UserModel): UserEntity {
        return UserEntity(
            id = model.id,
            name = model.name,
            username = model.username,
            email = model.email
        )
    }
}