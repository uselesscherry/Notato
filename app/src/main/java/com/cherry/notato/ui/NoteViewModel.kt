package com.cherry.notato.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherry.notato.domain.NoteRepository
import com.cherry.notato.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        getNotesJob = null
        getNotesJob = repository.getNotes().onEach { notes ->
            withContext(Dispatchers.IO) {
                _notes.value = notes
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getNoteById(id: Int) = repository.getNoteById(id)


    @Throws(EmptyFieldsException::class)
    fun insertNote(
        id: Int,
        title: String,
        content: String
    ) {
        when {
            title.isBlank() || content.isBlank() -> {
                throw EmptyFieldsException(message = "empty content or title")
            }
            title.isNotEmpty() && content.isNotEmpty() -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insert(
                        if (id != -1) Note(id, title, content.trim())
                        else Note(
                            title = title,
                            content = content.trim()
                        )
                    )
                }
            }
        }

    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(id)
        }
    }


    class EmptyFieldsException(message: String) : Exception(message)
}