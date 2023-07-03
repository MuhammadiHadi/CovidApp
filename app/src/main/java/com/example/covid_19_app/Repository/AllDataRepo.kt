package com.example.covid_19_app.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19_app.api.apiServices.ApiServices
import com.example.covid_19_app.model.AllData
import com.example.covid_19_app.utils.NetworkResult

class AllDataRepo(val apiServices : ApiServices) {

    private val _dataList= MutableLiveData<NetworkResult<AllData>>()
    val dataListLive: LiveData<NetworkResult<AllData>>
        get() = _dataList

    suspend fun getList(){ val result=apiServices.getAllData()
        if(result.isSuccessful){
            val response=result.body()
            println("response${response}")
            if(response!=null){
                _dataList.postValue( NetworkResult.Success(response))
            }
            else{
                _dataList.postValue(NetworkResult.Error( response.toString()))
            }

        }
    }
}