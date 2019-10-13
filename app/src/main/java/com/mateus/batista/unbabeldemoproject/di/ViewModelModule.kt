package com.mateus.batista.unbabeldemoproject.di

import com.mateus.batista.feature_post.detail.DetailPostViewModel
import com.mateus.batista.feature_post.list.ListPostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ ListPostViewModel() }
    viewModel { DetailPostViewModel() }
}