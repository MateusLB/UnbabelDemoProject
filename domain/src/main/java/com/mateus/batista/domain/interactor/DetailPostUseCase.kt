package com.mateus.batista.domain.interactor

import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import com.mateus.batista.domain.util.UseCase
import kotlinx.coroutines.CoroutineScope

class DetailPostUseCase(
    private val repository: PostRepository,
    scope: CoroutineScope
) : UseCase<Post, DetailPostUseCase.Params>(scope) {
    override suspend fun run(params: Params?): Response<Post> {
        requireNotNull(params) { "\"Params\" must not be null" }

        return repository.getPostById(params.postId)
    }
    
    data class Params(val postId: Long)
}