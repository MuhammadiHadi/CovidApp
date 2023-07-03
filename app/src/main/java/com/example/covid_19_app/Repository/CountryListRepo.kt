package com.example.covid_19_app.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19_app.api.apiServices.ApiServices
import com.example.covid_19_app.model.AllData
import com.example.covid_19_app.model.CountryListModelItem
import com.example.covid_19_app.model.CountryListModelX
import com.example.covid_19_app.utils.NetworkResult

class CountryListRepo (val apiServices : ApiServices){

    private val _countryList= MutableLiveData<NetworkResult<CountryListModelX>>()
    val countryList: LiveData<NetworkResult<CountryListModelX>>
        get() = _countryList

    suspend fun getCountryList(){ val result=apiServices.getCountryList()
        _countryList.postValue(NetworkResult.Loading())
        if(result.isSuccessful){
            val response=result.body()
            println("response${response}")
            if(response!=null){
                _countryList.postValue( NetworkResult.Success(response))
            }
            else{
                _countryList.postValue(NetworkResult.Error( response.toString()))
            }

        }
    }
}