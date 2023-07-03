package com.example.covid_19_app.model

data class AllData(
    val active: Float,
    val activePerOneMillion: Double,
    val affectedCountries: Int,
    val cases: Float,
    val casesPerOneMillion: Int,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Float,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val population: Long,
    val recovered: Float,
    val recoveredPerOneMillion: Double,
    val tests: Long,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)