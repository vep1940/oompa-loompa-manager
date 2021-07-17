package com.example.oompaloompamanager.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.domain.usecases.GetWorkerDetail
import com.example.oompaloompamanager.domain.usecases.GetWorkers
import com.example.oompaloompamanager.presentation.models.OompaLoompaDetailViewData
import com.example.oompaloompamanager.presentation.models.OompaLoompaViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWorkersUC: GetWorkers,
    private val getWorkerDetailUC: GetWorkerDetail
) : ViewModel() {

    private var nameFilter = ""
    private val professionFilters = mutableSetOf<String>()
    private val genderFilters = mutableSetOf<String>()

    private val workerTotalDataList = mutableListOf<OompaLoompaViewData>()
    private var workerDataList = listOf<OompaLoompaViewData>()
    private var page = 1

    private var _workerList = MutableLiveData<AppResponse<List<OompaLoompaViewData>>>()
    val workerList: LiveData<AppResponse<List<OompaLoompaViewData>>> get() = _workerList

    private var _workerDetail = MutableLiveData<AppResponse<OompaLoompaDetailViewData>>()
    val workerDetail: LiveData<AppResponse<OompaLoompaDetailViewData>> get() = _workerDetail


    fun getNextWorkers() {
        viewModelScope.launch {
            if (_workerList.value !is AppResponse.Loading) {
                _workerList.value = AppResponse.Loading
                getWorkersUC(page).collect { viewResponse ->
                    when (viewResponse) {
                        is AppResponse.ResponseOk -> {
                            workerTotalDataList.addAll(viewResponse.value)
                            checkFilters()
                            page++
                        }
                        is AppResponse.ResponseKo -> {
                            _workerList.value = viewResponse
                        }
                        is AppResponse.Loading -> {
                        }
                    }
                }
            }
        }
    }

    fun getProfessionFilters() = professionFilters

    fun filterWorkersByProfession(filterKey: String) {
        professionFilters.add(filterKey.lowercase())
        checkFilters()
    }

    fun removeFilterWorkersByProfession(filterKey: String) {
        professionFilters.remove(filterKey.lowercase())
        checkFilters()
    }

    fun getGenderFilters() = genderFilters

    fun filterWorkersByGender(filterKey: String) {
        genderFilters.add(filterKey.lowercase())
        checkFilters()
    }

    fun removeFilterWorkersByGender(filterKey: String) {
        genderFilters.remove(filterKey.lowercase())
        checkFilters()
    }

    fun getNameFilter() = nameFilter

    fun filterWorkersByName(filterKey: String) {
        nameFilter = filterKey.lowercase()
        checkFilters()
    }

    private fun checkFilters() {
        viewModelScope.launch{
            workerDataList = workerTotalDataList.filter { worker ->
                (professionFilters.isEmpty() || professionFilters.contains(worker.profession.lowercase())) &&
                (genderFilters.isEmpty() || genderFilters.contains(worker.gender.lowercase())) &&
                (nameFilter.isBlank() || (worker.firstName + " " + worker.lastName).contains(nameFilter, true))
            }
            _workerList.value = AppResponse.ResponseOk(workerDataList)
        }
    }

    fun clearWorkerErrors() {
        _workerList.value = AppResponse.ResponseOk(workerDataList.toList())
    }

    fun getWorkerDetail(id: Int) {
        viewModelScope.launch {
            if (_workerDetail.value !is AppResponse.Loading) {
                getWorkerDetailUC(id).collect {
                    _workerDetail.value = AppResponse.Loading
                    getWorkerDetailUC(id).collect { viewResponse ->
                        when (viewResponse) {
                            is AppResponse.ResponseOk -> {

                            }
                            else -> _workerDetail.value = viewResponse
                        }
                    }
                }
            }
        }
    }

}