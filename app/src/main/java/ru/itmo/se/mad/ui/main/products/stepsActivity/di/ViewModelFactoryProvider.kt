package ru.itmo.se.mad.ui.main.products.stepsActivity.di

import ru.itmo.se.mad.ui.main.products.stepsActivity.ActivityViewModel
import ru.itmo.se.mad.ui.main.products.stepsActivity.fit.FitApiService
import ru.itmo.se.mad.ui.main.products.stepsActivity.fit.FitRepository
import ru.itmo.se.mad.ui.main.products.stepsActivity.fit.FitRepositoryImpl

object ViewModelFactoryProvider {

    private val fitApiService by lazy {
        FitApiService()
    }

    private val fitRepository: FitRepository by lazy {
        FitRepositoryImpl(fitApiService)
    }

    fun provideActivityViewModelFactory(): ActivityViewModel.Factory {
        return ActivityViewModel.Factory(fitRepository)
    }
}