package com.example.oompaloompamanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.databinding.ListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: ListFragmentBinding? = null
    private val binding: ListFragmentBinding get() = _binding!!

    private lateinit var adapter: WorkerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvList.layoutManager = layoutManager
            adapter = WorkerAdapter()
            rvList.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.workerList.observe(viewLifecycleOwner){ response ->
            when (response){
                is AppResponse.ResponseOk -> {
                    binding.loading.visibility = View.GONE
                    adapter.submitList(response.value)
                }
                is AppResponse.ResponseKo -> {
                    binding.loading.visibility = View.GONE
                    showError(response.error)
                }
                is AppResponse.Loading -> { binding.loading.visibility = View.VISIBLE }
            }
        }

        binding.rvList.setOnScrollChangeListener { _, _, _, _, _ ->
            if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1)
                viewModel.getNextWorkers()
        }

        if (adapter.itemCount == 0){
            viewModel.getNextWorkers()
        }
    }

    // Fix -> If screen is rotated when an error is being captured, adapter can not recover data.
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearWorkerErrors()
    }

}