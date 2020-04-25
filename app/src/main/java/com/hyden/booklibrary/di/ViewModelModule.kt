package com.hyden.booklibrary.di

import com.hyden.booklibrary.view.MainViewModel
import com.hyden.booklibrary.view.comment.CommentViewModel
import com.hyden.booklibrary.view.common.LoadingViewModel
import com.hyden.booklibrary.view.detail.mysaved.SavedDetailViewModel
import com.hyden.booklibrary.view.detail.UnSavedDetailViewModel
import com.hyden.booklibrary.view.detail.feed.FeedDetailViewModel
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.booklibrary.view.home.HomeViewModel
import com.hyden.booklibrary.view.library.LibraryViewModel
import com.hyden.booklibrary.view.login.LoginViewModel
import com.hyden.booklibrary.view.myshared.MySharedBookViewMiodel
import com.hyden.booklibrary.view.note.NoteViewModel
import com.hyden.booklibrary.view.profile.ProfileViewModel
import com.hyden.booklibrary.view.search.SearchViewModel
import com.hyden.booklibrary.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // start
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }

    // main view
    viewModel { HomeViewModel(get(),get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FeedViewModel(get()) }
    viewModel { LibraryViewModel(get()) }
    viewModel { SettingViewModel(get(),get()) }
    viewModel { MySharedBookViewMiodel(get()) }

    // sub
    viewModel { UnSavedDetailViewModel(get()) }
    viewModel {
        SavedDetailViewModel(
            get(),
            get()
        )
    }
    viewModel { NoteViewModel(get(),get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { CommentViewModel() }
    viewModel { LoadingViewModel() }
    viewModel { FeedDetailViewModel() }

}