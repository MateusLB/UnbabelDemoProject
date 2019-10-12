package com.mateus.batista.data_remote.mapper

import com.mateus.batista.data.model.CommentModel
import com.mateus.batista.data_remote.model.CommentApi

object CommentMapper : RemoteMapper<List<CommentApi>, List<CommentModel>> {
    override fun toData(model: List<CommentApi>): List<CommentModel> {
        return model.map { parseComment(it) }
    }

    private fun parseComment(commentApi: CommentApi): CommentModel {
        return CommentModel(
            postId = commentApi.postId ?: 0,
            id = commentApi.id ?: 0,
            name = commentApi.name ?: "",
            email = commentApi.email ?: "",
            body = commentApi.body ?: ""
        )
    }
}