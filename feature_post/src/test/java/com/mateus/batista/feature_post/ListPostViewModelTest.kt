package com.mateus.batista.feature_post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mateus.batista.base_feature.util.FlowState
import com.mateus.batista.domain.interactor.ListPostUseCase
import com.mateus.batista.domain.model.Post
import com.mateus.batista.feature_post.list.ListPostViewModel
import com.mateus.batista.feature_post.mapper.ListPostMapper
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ListPostViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListPostViewModel

    private val useCase = mockk<ListPostUseCase>()
    private val testModule = module { single { useCase } }

    @Before
    fun setup() {
        startKoin { modules(testModule) }
        viewModel = ListPostViewModel()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `getPosts must postLoading when called`() {
        every { useCase.execute(onError = any(), onSuccess = any()) } just Runs

        viewModel.getPosts()
        assertEquals(FlowState.Status.LOADING, viewModel.getListPostStatus().value?.status)
    }

    @Test
    fun `getPosts with success response must postSuccess with data mapped`() {
        val dummyPosts = listOf<Post>()
        stubSuccess(dummyPosts)

        viewModel.getPosts()

        assertEquals(FlowState.Status.SUCCESS, viewModel.getListPostStatus().value?.status)
        assertEquals(
            viewModel.getListPostStatus().value?.data,
            ListPostMapper.toFeature(dummyPosts)
        )

    }

    @Test
    fun `getPosts with failure response must postError with throwable`() {
        val dummyError = Throwable()
        stubError(dummyError)

        viewModel.getPosts()

        assertEquals(FlowState.Status.ERROR, viewModel.getListPostStatus().value?.status)
        assertEquals(viewModel.getListPostStatus().value?.error, dummyError)
    }

    private fun stubSuccess(posts: List<Post>) {
        every { useCase.execute(onError = any(), onSuccess = captureLambda()) } answers {
            lambda<(List<Post>) -> Unit>().invoke(posts)
        }
    }

    private fun stubError(error: Throwable) {
        every { useCase.execute(onError = captureLambda(), onSuccess = any()) } answers {
            lambda<(Throwable) -> Unit>().invoke(error)
        }
    }
}