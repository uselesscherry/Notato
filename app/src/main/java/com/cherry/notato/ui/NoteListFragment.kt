package com.cherry.notato.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cherry.notato.R
import com.cherry.notato.databinding.FragmentNoteListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NoteListFragment : Fragment() {

    private val viewModel: NoteViewModel by sharedViewModel()

    private lateinit var noteListAdapter: NoteListAdapter

    private lateinit var binding: FragmentNoteListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        setViewsUp()
        return binding.root
    }

    private fun setViewsUp() {
        binding.addNoteButton.setOnClickListener {
            navigateToAddEditNoteFragment()
        }
        setupNoteListAdapter()
    }

    private fun setupNoteListAdapter() {
        lifecycleScope.launchWhenStarted {
            viewModel.notes.collect { notes ->
                noteListAdapter = NoteListAdapter(
                    notes = notes,
                    noteItemClickListener = { noteId ->
                        navigateToAddEditNoteFragment(noteId)
                    },
                    noteDeleteClickListener = { noteId ->
                        showDeleteDialog(noteId)
                    }
                )
                binding.noteListRecyclerView.adapter = noteListAdapter
            }
        }
    }

    private fun navigateToAddEditNoteFragment(id: Int = -1) {
        val args = Bundle().apply {
            putInt("noteId", id)
        }
        findNavController().navigate(R.id.action_noteListFragment_to_addEditNoteFragment, args)
    }

    private fun showDeleteDialog(noteId: Int) {
        val dialog = createDeleteDialog(noteId)
        dialog.show()
    }

    private fun createDeleteDialog(noteId: Int) = DeleteDialog(
        context = requireContext(),
        onDeleteApproved = {
            viewModel.deleteNote(noteId)
        }
    )

}