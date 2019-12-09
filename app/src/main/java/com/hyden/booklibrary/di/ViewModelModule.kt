package com.hyden.booklibrary.di

import com.hyden.booklibrary.view.detail.DetailViewModel
import com.hyden.booklibrary.view.home.HomeViewModel
import com.hyden.booklibrary.view.library.LibraryViewModel
import com.hyden.booklibrary.view.search.SearchViewModel
import com.hyden.booklibrary.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LibraryViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { DetailViewModel(get()) }
}