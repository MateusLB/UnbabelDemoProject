package com.mateus.batista.feature_post.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateus.batista.base_feature.BaseViewModel
import com.mateus.batista.base_feature.util.*
import com.mateus.batista.domain.interactor.ListPostUseCase
import com.mateus.batista.feature_post.mapper.ListPostMapper
import com.mateus.batista.feature_post.model.ListPost

class ListPostViewModel : BaseViewModel() {

    private val listPostStatus by lazy { MutableLiveData<FlowState<List<ListPost>>>() }
    fun getListPostStatus(): LiveData<FlowState<List<ListPost>>> = listPostStatus

    private val useCase: ListPostUseCase by useCase()

    fun getPosts() {
        listPostStatus.postLoading()
        useCase.execute(
            onSuccess = { listPostStatus.postSuccess(ListPostMapper.toFeature(it)) },
            onError = { listPostStatus.postError(it) }
        )
    }
}