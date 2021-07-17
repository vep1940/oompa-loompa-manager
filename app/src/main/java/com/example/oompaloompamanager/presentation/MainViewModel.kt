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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWorkersUC: GetWorkers,
    private val getWorkerDetailUC: GetWorkerDetail
) : ViewModel() {

    private val workerDataList = HashMap<Int, OompaLoompaViewData>()
    private var page = 1

    private var _workerList = MutableLiveData<AppResponse<List<OompaLoompaViewData>>>()
    val workerList: LiveData<AppResponse<List<OompaLoompaViewData>>> get() = _workerList

    private var _workerDetail = MutableLiveData<AppResponse<OompaLoompaDetailViewData>>()
    val workerDetail: LiveData<AppResponse<OompaLoompaDetailViewData>> get() = _workerDetail

    fun clearWorkerErrors(){
        _workerList.value = AppResponse.ResponseOk(workerDataList.values.toList())
    }

    fun getNextWorkers() {
        viewModelScope.launch {
            if (_workerList.value !is AppResponse.Loading) {
                _workerList.value = AppResponse.Loading
                getWorkersUC(page).collect { viewResponse ->
                    when (viewResponse) {
                        is AppResponse.ResponseOk -> {
                            workerDataList.putAll(viewResponse.value.map { value ->
                                Pair(
                                    value.id,
                                    value
                                )
                            })
                            _workerList.value =
                                AppResponse.ResponseOk(workerDataList.values.toList())
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