package com.cherry.notato.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherry.notato.databinding.NoteItemBinding
import com.cherry.notato.domain.model.Note

class NoteListAdapter(
    private val notes: List<Note>,
    private val noteItemClickListener: (noteId: Int) -> Unit,
    private val noteDeleteClickListener: (noteId: Int) -> Unit,
) : RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    private lateinit var binding: NoteItemBinding

    class NoteListViewHolder(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.binding.textViewNoteTitle.text = notes[position].title
        holder.binding.textViewNoteContent.text = notes[position].content
        setListeners(position)
    }

    private fun setListeners(position: Int) {
        notes[position].id?.let { noteId ->
            binding.noteItemCard.setOnClickListener {
                noteItemClickListener(noteId)
            }
            binding.buttonDeleteNoteIcon.setOnClickListener {
                noteDeleteClickListener(noteId)
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}