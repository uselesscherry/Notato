package com.cherry.notato.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.cherry.notato.R
import com.cherry.notato.databinding.DeleteDialogBinding

class DeleteDialog(
    context: Context,
    private val onDeleteApproved: () -> Unit
) : Dialog(context, R.style.DeleteDialog) {

    private lateinit var binding: DeleteDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeleteDialogBinding.inflate(layoutInflater)
        setupListeners()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.buttonApproveDeletingNote.setOnClickListener {
            onDeleteApproved()
            dismiss()
        }
        binding.buttonCancelDeletion.setOnClickListener {
            dismiss()
        }
    }

}