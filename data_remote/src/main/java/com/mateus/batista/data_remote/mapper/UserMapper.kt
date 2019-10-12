package com.mateus.batista.data_remote.mapper

import com.mateus.batista.data.model.UserModel
import com.mateus.batista.data_remote.model.UserApi

object UserMapper : RemoteMapper<List<UserApi>, List<UserModel>> {
    override fun toData(model: List<UserApi>): List<UserModel> {
        return model.map { parseUser(it) }
    }

    private fun parseUser(userApi: UserApi): UserModel {
        return UserModel(
            id = userApi.id ?: 0,
            name = userApi.name ?: "",
            username = userApi.username ?: "",
            email = userApi.email ?: ""
        )
    }
}