package com.mateus.batista.domain

import com.mateus.batista.domain.interactor.ListPostUseCase
import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import com.mateus.batista.domain.util.testModule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class ListPostUseCaseTest : KoinTest {

    private val repository = mock<PostRepository>()
    private lateinit var useCase: ListPostUseCase

    @Before
    fun setup() {
        startKoin { modules(testModule) }
        useCase = ListPostUseCase(repository, CoroutineScope(Dispatchers.Unconfined))
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `ListPostUseCase return a Success response`() = runBlocking {
        val dummyPosts = listOf<Post>()
        stubRepositorySuccess(dummyPosts)

        val response = useCase.run()
        assertTrue(response is Response.Success && response.data == dummyPosts)
    }

    @Test
    fun `ListPostUseCase return a Error response`() = runBlocking {
        val dummyError = RuntimeException()
        stubRepositoryError(dummyError)

        val response = useCase.run()
        assertTrue(response is Response.Error && response.exception == dummyError)
    }

    private suspend fun stubRepositorySuccess(posts: List<Post>) {
        whenever(repository.getPosts()).thenReturn(Response.Success(posts))
    }

    private suspend fun stubRepositoryError(error: Throwable) {
        whenever(repository.getPosts()).thenReturn(Response.Error(error))
    }
}