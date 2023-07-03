package com.example.covid_19_app.api.apiServices

import com.example.covid_19_app.model.AllData
import com.example.covid_19_app.model.CountryListModelItem
import com.example.covid_19_app.model.CountryListModelX
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("all")
    suspend fun getAllData():Response<AllData>

    @GET("countries")
    suspend fun  getCountryList():Response<CountryListModelX>
}



