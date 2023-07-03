package com.example.covid_19_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covid_19_app.Repository.CountryListRepo

class CountryListVMFactory(private val countryListRepo : CountryListRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryListViewModel(countryListRepo) as T
    }

}