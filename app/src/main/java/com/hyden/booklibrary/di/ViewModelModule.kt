package com.hyden.booklibrary.di

import com.hyden.booklibrary.view.MainViewModel
import com.hyden.booklibrary.view.detail.SavedDetailViewModel
import com.hyden.booklibrary.view.detail.UnSavedDetailViewModel
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.booklibrary.view.home.HomeViewModel
import com.hyden.booklibrary.view.library.LibraryViewModel
import com.hyden.booklibrary.view.note.NoteViewModel
import com.hyden.booklibrary.view.search.SearchViewModel
import com.hyden.booklibrary.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { LibraryViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { UnSavedDetailViewModel(get()) }
    viewModel { SavedDetailViewModel(get()) }
    viewModel { NoteViewModel(get()) }
    viewModel { FeedViewModel() }
    viewModel { MainViewModel() }
}