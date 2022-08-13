package com.cherry.notato.di

import androidx.room.Room
import com.cherry.notato.data.NoteRepositoryImpl
import com.cherry.notato.data.local.NoteDatabase
import com.cherry.notato.domain.NoteRepository
import com.cherry.notato.ui.AddEditNoteFragment
import com.cherry.notato.ui.NoteListFragment
import com.cherry.notato.ui.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            NoteDatabase.DB_NAME
        ).build()
    }

    single<NoteRepository> { NoteRepositoryImpl(get()) }

    viewModel { NoteViewModel(get()) }

    fragment { NoteListFragment() }
    fragment { AddEditNoteFragment() }

}