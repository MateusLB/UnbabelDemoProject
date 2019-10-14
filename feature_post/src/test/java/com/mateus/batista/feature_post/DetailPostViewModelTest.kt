package com.mateus.batista.feature_post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mateus.batista.base_feature.util.FlowState
import com.mateus.batista.domain.interactor.DetailPostUseCase
import com.mateus.batista.domain.model.Post
import com.mateus.batista.feature_post.detail.DetailPostViewModel
import com.mateus.batista.feature_post.mapper.DetailPostMapper
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
class DetailPostViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailPostViewModel

    private val useCase = mockk<DetailPostUseCase>()
    private val testModule = module { single { useCase } }

    @Before
    fun setup() {
        startKoin { modules(testModule) }
        viewModel = DetailPostViewModel()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `getPost must postLoading when called`() {
        every { useCase.execute(params = any(), onSuccess = any(), onError = any()) } just Runs

        viewModel.getPost(1)

        assertEquals(FlowState.Status.LOADING, viewModel.getPostStatus().value?.status)
    }

    @Test
    fun `getPost with success response must postSuccess with data mapped`() {
        val dummyPost = Post(1, "", "", "", 2)
        stubSuccess(dummyPost)

        viewModel.getPost(1)

        assertEquals(FlowState.Status.SUCCESS, viewModel.getPostStatus().value?.status)
        assertEquals(viewModel.getPostStatus().value?.data, DetailPostMapper.toFeature(dummyPost))

    }

    @Test
    fun `getPost with failure response must postError with throwable`() {
        val dummyError = Throwable()
        stubError(dummyError)

        viewModel.getPost(1)

        assertEquals(FlowState.Status.ERROR, viewModel.getPostStatus().value?.status)
        assertEquals(viewModel.getPostStatus().value?.error, dummyError)
    }

    private fun stubSuccess(post: Post) {
        every {
            useCase.execute(
                params = any(),
                onError = any(),
                onSuccess = captureLambda()
            )
        } answers {
            lambda<(Post) -> Unit>().invoke(post)
        }
    }

    private fun stubError(error: Throwable) {
        every {
            useCase.execute(
                params = any(),
                onError = captureLambda(),
                onSuccess = any()
            )
        } answers {
            lambda<(Throwable) -> Unit>().invoke(error)
        }
    }
}