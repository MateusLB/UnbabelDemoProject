package com.mateus.batista.domain.interactor

import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import com.mateus.batista.domain.util.UseCase
import kotlinx.coroutines.CoroutineScope

class ListPostUseCase(
    private val repository: PostRepository,
    scope: CoroutineScope
) : UseCase<List<Post>, Unit>(scope)  {

    override suspend fun run(params: Unit?): Response<List<Post>> {
        return repository.getPosts()
    }
}