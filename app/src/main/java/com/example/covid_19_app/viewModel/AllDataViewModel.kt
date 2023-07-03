package com.example.covid_19_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid_19_app.Repository.AllDataRepo
import com.example.covid_19_app.model.AllData
import com.example.covid_19_app.utils.NetworkResult
import kotlinx.coroutines.launch

class AllDataViewModel(private val allDataRepo : AllDataRepo):ViewModel() {
    val dataList: LiveData<NetworkResult<AllData>>
        get()=allDataRepo.dataListLive
    fun getData(){
        viewModelScope.launch () {
            allDataRepo.getList()
        }
    }


}