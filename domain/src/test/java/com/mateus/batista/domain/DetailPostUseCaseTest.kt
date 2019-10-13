package com.mateus.batista.domain

import com.mateus.batista.domain.interactor.DetailPostUseCase
import com.mateus.batista.domain.model.Post
import com.mateus.batista.domain.repository.PostRepository
import com.mateus.batista.domain.util.Response
import com.mateus.batista.domain.util.testModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class DetailPostUseCaseTest : KoinTest {

    private val repository = mock<PostRepository>()
    private lateinit var useCase: DetailPostUseCase

    @Before
    fun setup() {
        startKoin { modules(testModule) }
        useCase = DetailPostUseCase(repository, CoroutineScope(Dispatchers.Unconfined))
    }

    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun `DetailPostUseCase when pass valid params must call repository`() = runBlocking<Unit> {
        val dummyPost = Post(1,1,"","")
        stubRepositorySuccess(dummyPost)

        useCase.run(DetailPostUseCase.Params(1))
        verify(repository).getPostById(1)
    }

    @Test
    fun `DetailPostUseCase when missing params must call throws exception`() = runBlocking {
        val error = assertFailsWith<IllegalArgumentException> { useCase.run() }
        assertEquals("\"Params\" must not be null", error.message)
    }

    @Test
    fun `DetailPostUseCase return a Success response`() = runBlocking {
        val dummyPost = Post(1,1,"","")
        stubRepositorySuccess(dummyPost)

        val response = useCase.run(DetailPostUseCase.Params(1))
        assertTrue(response is Response.Success && response.data == dummyPost)
    }

    @Test
    fun `DetailPostUseCase return a Error response`() = runBlocking {
        val dummyError = RuntimeException()
        stubRepositoryError(dummyError)

        val response = useCase.run(DetailPostUseCase.Params(1))
        assertTrue(response is Response.Error && response.exception == dummyError)
    }

    private suspend fun stubRepositorySuccess(post: Post) {
        whenever(repository.getPostById(any())).thenReturn(Response.Success(post))
    }

    private suspend fun stubRepositoryError(error: Throwable) {
        whenever(repository.getPostById(any())).thenReturn(Response.Error(error))
    }
}