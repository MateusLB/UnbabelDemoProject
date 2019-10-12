package com.mateus.batista.data_remote.mapper

import com.mateus.batista.data.model.PostModel
import com.mateus.batista.data_remote.model.PostApi

object PostMapper : RemoteMapper<List<PostApi>, List<PostModel>> {
    override fun toData(model: List<PostApi>): List<PostModel> {
        return model.map { parsePost(it) }
    }

    private fun parsePost(postApi: PostApi): PostModel {
        return PostModel(
            userId = postApi.userId ?: 0,
            id = postApi.id ?: 0,
            title = postApi.title ?: "",
            body = postApi.body ?: ""
        )
    }
}