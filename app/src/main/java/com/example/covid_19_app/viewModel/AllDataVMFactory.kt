package com.example.covid_19_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covid_19_app.Repository.AllDataRepo

class AllDataVMFactory(val dataListRepo : AllDataRepo ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllDataViewModel(dataListRepo) as T
        }

    }