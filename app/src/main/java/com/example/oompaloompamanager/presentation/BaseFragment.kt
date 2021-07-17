package com.example.oompaloompamanager.presentation

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oompaloompamanager.R
import com.example.oompaloompamanager.domain.constants.AppError

abstract class BaseFragment : Fragment() {

    private var toast: Toast? = null

    fun showError(error: AppError){
        toast?.apply {
            this.cancel()
        }
        Toast.makeText(
            context,
            getString(
                R.string.error_message,
                error.code.toString(),
                error.message
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
}