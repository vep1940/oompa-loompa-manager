package com.example.oompaloompamanager.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.oompaloompamanager.R
import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.commons.loadImage
import com.example.oompaloompamanager.databinding.DetailFragmentBinding
import com.example.oompaloompamanager.presentation.models.OompaLoompaDetailViewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

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

    override fun onResume() {
        super.onResume()

        viewModel.workerDetail.observe(viewLifecycleOwner) { response ->
            when (response) {
                is AppResponse.ResponseOk -> {
                    fillData(response.value)
                    binding.loading.visibility = View.GONE
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

        viewModel.getWorkerDetail()
    }

    private fun fillData(data: OompaLoompaDetailViewData) {
        CoroutineScope(Dispatchers.Main).launch {
            with(binding) {
                ivWorkerDetail.loadImage(
                    requireView(),
                    data.image,
                    R.drawable.ic_void_image,
                    R.drawable.ic_broken_image
                )
                tvName.setText(getString(R.string.worker_name, data.firstName, data.lastName))
                tvProfession.setText(data.profession)
                tvAge.setText(data.age)
                tvGender.setText(data.gender)
                tvHeight.setText(data.height)
                tvEmail.setText(data.email)
                tvColor.setText(data.favoriteColor)
                tvFood.setText(data.favoriteFood)
                tvQuotaInfo.text = data.quota
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearWorkerDetail()
        _binding = null
    }
}