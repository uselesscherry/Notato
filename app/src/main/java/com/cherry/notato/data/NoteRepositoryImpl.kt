package com.cherry.notato.data

import com.cherry.notato.data.local.NoteDatabase
import com.cherry.notato.domain.NoteRepository
import com.cherry.notato.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl (
    db: NoteDatabase
) : NoteRepository {

    private val noteDao = db.noteDao

    override fun getNotes(): Flow<List<Note>> = noteDao.getNotes()

    override suspend fun getNoteById(id: Int): Note = noteDao.getNoteById(id)

    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun delete(id: Int) {
        noteDao.deleteNoteById(id)
    }

}