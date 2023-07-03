package com.example.covid_19_app.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19_app.R
import com.example.covid_19_app.Repository.CountryListRepo
import com.example.covid_19_app.api.apiServices.ApiServices
import com.example.covid_19_app.api.retrofitHelper.RetrofitHelper
import com.example.covid_19_app.databinding.FragmentCountriesListBinding
import com.example.covid_19_app.model.CountryListModelItem
import com.example.covid_19_app.model.CountryListModelX
import com.example.covid_19_app.ui.adapter.CountryListAdapter
import com.example.covid_19_app.ui.base.BaseFragment
import com.example.covid_19_app.utils.Constants
import com.example.covid_19_app.utils.NetworkResult
import com.example.covid_19_app.viewModel.CountryListVMFactory
import com.example.covid_19_app.viewModel.CountryListViewModel


class CountriesListFragment : BaseFragment<FragmentCountriesListBinding>(R.layout.fragment_countries_list) {

    private lateinit var viewModel : CountryListViewModel
    override fun setContentToView(binding : FragmentCountriesListBinding) {
        val result = RetrofitHelper().getAllData().create(ApiServices::class.java)
        val repoList = CountryListRepo(result)
        viewModel = ViewModelProvider(this ,
            CountryListVMFactory(repoList)).get(CountryListViewModel::class.java)
        viewModel.getCountryList()
        binding.progressBar.visibility= View.VISIBLE
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            val response = it.data as CountryListModelX
            when(it){
                is NetworkResult.Success->{
                    binding.apply {
                        progressBar.visibility=View.INVISIBLE
                    rcCountryList.layoutManager=LinearLayoutManager(requireContext())
                    rcCountryList.setHasFixedSize(true)
                        val adapter = CountryListAdapter(response) { position ->
                            val clickedItem = response[position]
                            val bundle = Bundle().apply {
                                putString(Constants.FlAG, clickedItem.countryInfo.flag)
                                putString(Constants.COUNTRY, clickedItem.country)
                                putString(Constants.CASES, clickedItem.cases)
                                putString(Constants.ACTIVE, clickedItem.active.toString())
                                putString(Constants.RECOVERED, clickedItem.recovered.toString())
                                putString(Constants.DEATHS, clickedItem.deaths.toString())
                            }
                            findNavController().navigate(R.id.action_allCountryFragment_to_countryStateFragment,bundle)
                            // Handle the click event here
                            // Perform your desired action based on the clicked item position
                        }
                       rcCountryList.adapter=adapter
                        search.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                            override fun afterTextChanged(s: Editable?) {
                                val query = s.toString().trim()
                                adapter.filter(query)
                            }
                        })
                    }
                }
                is NetworkResult.Error->{ binding.progressBar.visibility=View.INVISIBLE}
                is NetworkResult.Loading->{}
            }
        })



    }
    }



