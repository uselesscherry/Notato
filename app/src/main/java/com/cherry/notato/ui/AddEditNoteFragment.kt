package com.cherry.notato.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cherry.notato.R
import com.cherry.notato.databinding.FragmentAddEditNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private lateinit var binding: FragmentAddEditNoteBinding

    private val viewModel: NoteViewModel by sharedViewModel()
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        setViewsUp()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (noteId != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                val (_, noteTitle, noteContent) = viewModel.getNoteById(noteId)
                binding.editTextNoteTitle.setText(noteTitle)
                binding.editTextNoteContent.setText(noteContent)

            }
        }
    }

    private fun setViewsUp() {
        binding.saveNoteButton.setOnClickListener {
            try {
                viewModel.insertNote(
                    id = noteId,
                    title = binding.editTextNoteTitle.text.toString(),
                    content = binding.editTextNoteContent.text.toString()
                )
                navigateToNoteListFragment()
            } catch (e: NoteViewModel.EmptyFieldsException) {
                Toast.makeText(
                    context,
                    "You need to enter note's title and content",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToNoteListFragment() {
        findNavController().navigate(R.id.action_addEditNoteFragment_to_noteListFragment)
    }

}