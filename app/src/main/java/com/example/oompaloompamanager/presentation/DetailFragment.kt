package com.example.oompaloompamanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.oompaloompamanager.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: DetailFragmentBinding? = null
    private val binding: DetailFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}