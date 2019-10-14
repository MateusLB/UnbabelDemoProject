package com.mateus.batista.feature_post.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateus.batista.base_feature.BaseViewModel
import com.mateus.batista.base_feature.util.*
import com.mateus.batista.domain.interactor.DetailPostUseCase
import com.mateus.batista.feature_post.mapper.DetailPostMapper
import com.mateus.batista.feature_post.model.DetailPost

class DetailPostViewModel : BaseViewModel() {

    private val postStatus by lazy { MutableLiveData<FlowState<DetailPost>>() }
    fun getPostStatus(): LiveData<FlowState<DetailPost>> = postStatus

    private val useCase: DetailPostUseCase by useCase()

    fun getPost(id: Long) {
        postStatus.postLoading()
        useCase.execute(
            params = DetailPostUseCase.Params(id),
            onSuccess = { postStatus.postSuccess(DetailPostMapper.toFeature(it)) },
            onError = { postStatus.postError(it) }
        )
    }
}