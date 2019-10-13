package com.mateus.batista.domain.util

import com.mateus.batista.domain.util.TestContextProvider
import com.mateus.batista.domain.util.ThreadContextProvider
import org.koin.dsl.module

val testModule = module {
    single { TestContextProvider() as ThreadContextProvider }
}