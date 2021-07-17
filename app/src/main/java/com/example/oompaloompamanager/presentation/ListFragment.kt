package com.example.oompaloompamanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.databinding.ListFragmentBinding
import com.example.oompaloompamanager.domain.constants.Gender
import com.example.oompaloompamanager.domain.constants.Profession
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: ListFragmentBinding? = null
    private val binding: ListFragmentBinding get() = _binding!!

    private lateinit var adapter: WorkerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private enum class FilterType {
        PROFESSION,
        GENDER
    }

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
        with(binding) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvList.layoutManager = layoutManager
            adapter = WorkerAdapter()
            rvList.adapter = adapter

            ivFilterButton.setOnClickListener {
                clFilterChips.visibility = if (clFilterChips.isVisible) View.GONE else View.VISIBLE
            }

            chipGenderFemale.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.GENDER,
                    Gender.FEMALE.value,
                    isChecked
                )
            }

            chipGenderMale.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.GENDER,
                    Gender.MALE.value,
                    isChecked
                )
            }

            chipProfessionDeveloper.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.PROFESSION,
                    Profession.DEVELOPER.value,
                    isChecked
                )
            }

            chipProfessionMetalworker.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.PROFESSION,
                    Profession.METALWORKER.value,
                    isChecked
                )
            }

            chipProfessionMedic.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.PROFESSION,
                    Profession.MEDIC.value,
                    isChecked
                )
            }

            chipProfessionGemcutter.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.PROFESSION,
                    Profession.GEMCUTTER.value,
                    isChecked
                )
            }

            chipProfessionBrewer.setOnCheckedChangeListener { _, isChecked ->
                setFilterFunctionListeners(
                    FilterType.PROFESSION,
                    Profession.BREWER.value,
                    isChecked
                )
            }

            svFilterList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filterWorkersByName(newText ?: "")
                    return false
                }
            })
        }
    }

    private fun setFilterFunctionListeners(
        filterType: FilterType,
        filterKey: String,
        isChecked: Boolean
    ) {
        when (filterType) {
            FilterType.PROFESSION -> {
                if (isChecked) viewModel.filterWorkersByProfession(filterKey) else
                    viewModel.removeFilterWorkersByProfession(
                        filterKey
                    )
            }
            FilterType.GENDER -> {
                if (isChecked) viewModel.filterWorkersByGender(filterKey) else
                    viewModel.removeFilterWorkersByGender(
                        filterKey
                    )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.workerList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is AppResponse.ResponseOk -> {
                    binding.loading.visibility = View.GONE
                    adapter.submitList(response.value)
                }
                is AppResponse.ResponseKo -> {
                    binding.loading.visibility = View.GONE
                    showError(response.error)
                }
                is AppResponse.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        binding.rvList.setOnScrollChangeListener { _, _, _, _, _ ->
            if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1)
                viewModel.getNextWorkers()
        }

        if (adapter.itemCount == 0) {
            viewModel.getNextWorkers()
        }

        with(binding) {
            val genderFilters = viewModel.getGenderFilters()
            chipGenderMale.isChecked = genderFilters.contains(Gender.MALE.value)
            chipGenderFemale.isChecked = genderFilters.contains(Gender.FEMALE.value)

            val professionFilters = viewModel.getProfessionFilters()
            chipProfessionDeveloper.isChecked =
                professionFilters.contains(Profession.DEVELOPER.value)
            chipProfessionMetalworker.isChecked =
                professionFilters.contains(Profession.METALWORKER.value)
            chipProfessionMedic.isChecked = professionFilters.contains(Profession.MEDIC.value)
            chipProfessionGemcutter.isChecked =
                professionFilters.contains(Profession.GEMCUTTER.value)
            chipProfessionBrewer.isChecked = professionFilters.contains(Profession.BREWER.value)

            svFilterList.setQuery(viewModel.getNameFilter(), false)
        }
    }

    // Fix -> If screen is rotated when an error is being captured, adapter can not recover data.
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearWorkerErrors()
    }

}