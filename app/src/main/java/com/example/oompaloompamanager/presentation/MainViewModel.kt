package com.example.oompaloompamanager.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _workerList = MutableLiveData<List<OompaLoompaViewData>>(listOf())
    val workerList: LiveData<List<OompaLoompaViewData>> get() = _workerList

    private var _workerDetail = MutableLiveData<OompaLoompaDetailViewData>()
    val workerDetail: LiveData<OompaLoompaDetailViewData> get() = _workerDetail

    //TODO -> LOADING / GESTION ERRORES

    fun getWorkers(page: Int){
        viewModelScope.launch {
            getWorkersUC(page).collect {

            }
        }
    }

    fun getWorkerDetail(id: Int){
        viewModelScope.launch {
            getWorkerDetailUC(id).collect {

            }
        }
    }

}