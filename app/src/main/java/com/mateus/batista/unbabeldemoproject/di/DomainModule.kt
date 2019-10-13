package com.mateus.batista.unbabeldemoproject.di

import com.mateus.batista.domain.interactor.DetailPostUseCase
import com.mateus.batista.domain.interactor.ListPostUseCase
import com.mateus.batista.domain.util.ThreadContextProvider
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

val domainModule = module {
    factory { (scope : CoroutineScope) -> ListPostUseCase(get(), scope) }
    factory { (scope : CoroutineScope) -> DetailPostUseCase(get(), scope) }
    factory { ThreadContextProvider() }
}