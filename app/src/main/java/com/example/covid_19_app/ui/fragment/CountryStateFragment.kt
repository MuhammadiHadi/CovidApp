package com.example.covid_19_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.bumptech.glide.Glide
import com.example.covid_19_app.R
import com.example.covid_19_app.databinding.FragmentCountryStateBinding
import com.example.covid_19_app.ui.adapter.imageUrl
import com.example.covid_19_app.ui.base.BaseFragment
import com.example.covid_19_app.utils.Constants

class CountryStateFragment : BaseFragment<FragmentCountryStateBinding>(R.layout.fragment_country_state){
    override fun setContentToView(binding : FragmentCountryStateBinding) {


        binding.apply {


            tvCountryName.text=arguments?.getString(Constants.COUNTRY).toString()
            tvActiveCases.text=arguments?.getString(Constants.ACTIVE).toString()
            tvCasesTotal.text=arguments?.getString(Constants.CASES).toString()
            recovered.text=arguments?.getString(Constants.RECOVERED).toString()
            deaths.text=arguments?.getString(Constants.DEATHS).toString()
            val imageUrl = arguments?.getString(Constants.FlAG)
            imageUrl?.let {
                Glide.with(requireContext())
                    .load(it)
                    .into(ivFlag)
            }

        }

    }


}