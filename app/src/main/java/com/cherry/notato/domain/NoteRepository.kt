package com.cherry.notato.domain

import com.cherry.notato.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note

    suspend fun insert(note: Note)

    suspend fun delete(id: Int)
}