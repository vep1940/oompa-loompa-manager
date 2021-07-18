package com.example.oompaloompamanager.presentation

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.oompaloompamanager.BuildConfig
import com.example.oompaloompamanager.R
import com.example.oompaloompamanager.domain.constants.AppError

abstract class BaseFragment : Fragment() {

    private var toast: Toast? = null

    fun showError(error: AppError) {
        if (BuildConfig.DEBUG) {
            Log.d(
                "AppError",
                getString(R.string.error_message, error.code.toString(), error.message)
            )
        }
        toast?.apply {
            this.cancel()
        }
        Toast.makeText(
            context,
            getMessageError(error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getMessageError(appError: AppError): String {
        return when (appError) {
            AppError.UNSUCCESSFUL_REQUEST_ERROR, AppError.EMPTY_BODY_RESPONSE_ERROR, AppError.NO_INTERNET_ERROR, AppError.HTTP_ERROR -> {
                getString(R.string.request_failure_error)
            }
            AppError.NO_MORE_PAGES_ERROR -> {
                getString(R.string.no_more_workers_error)
            }
            AppError.PARSING_ERROR, AppError.UNKNOWN_ERROR -> {
                getString(R.string.unknown_error)
            }
        }
    }
}