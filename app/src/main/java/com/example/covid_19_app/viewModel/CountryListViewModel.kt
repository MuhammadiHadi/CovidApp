package com.example.covid_19_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid_19_app.Repository.CountryListRepo
import com.example.covid_19_app.model.AllData
import com.example.covid_19_app.model.CountryListModelItem
import com.example.covid_19_app.model.CountryListModelX
import com.example.covid_19_app.utils.NetworkResult
import kotlinx.coroutines.launch

class CountryListViewModel(private val countryListRepo : CountryListRepo) :ViewModel(){
    val countryList: LiveData<NetworkResult<CountryListModelX>>
        get()=countryListRepo.countryList
    fun getCountryList(){
        viewModelScope.launch () {
            countryListRepo.getCountryList()
        }
    }
}