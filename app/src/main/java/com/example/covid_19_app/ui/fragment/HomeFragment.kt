package com.example.covid_19_app.ui.fragment

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.covid_19_app.R
import com.example.covid_19_app.Repository.AllDataRepo
import com.example.covid_19_app.api.apiServices.ApiServices
import com.example.covid_19_app.api.retrofitHelper.RetrofitHelper
import com.example.covid_19_app.databinding.FragmentHomeBinding
import com.example.covid_19_app.ui.base.BaseFragment
import com.example.covid_19_app.utils.Constants
import com.example.covid_19_app.utils.NetworkResult
import com.example.covid_19_app.viewModel.AllDataVMFactory
import com.example.covid_19_app.viewModel.AllDataViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun setContentToView(binding : FragmentHomeBinding) {
        lateinit var viewModel:AllDataViewModel
        val result = RetrofitHelper().getAllData().create(ApiServices::class.java)
        val repoList = AllDataRepo(result)
        viewModel = ViewModelProvider(this ,
            AllDataVMFactory(repoList)).get(AllDataViewModel::class.java)
        viewModel.getData()
        binding.pbLoading.visibility=View.VISIBLE
        binding.apply {
            btnCountryTrack.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_allCountryFragment)
            }
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success->{
                     pbLoading.visibility=View.INVISIBLE
                    tvTotal.text=it.data!!.cases.toString()
                    tvActive.text= it.data.active.toString()
                    tvRecovered.text= it.data.recovered.toString()
                    tvDeaths.text= it.data.deaths.toString()
                    tvCritical.text= it.data.critical.toString()
                    tvTotalRecovered.text= it.data.todayRecovered.toString()
                    tvTotalDeaths.text= it.data.todayDeaths.toString()
                    //pieChart value
                    setPieChart(it.data.active,it.data.recovered,it.data.cases)
                }
                is NetworkResult.Error->{

                    pbLoading.visibility=View.INVISIBLE
                }
                is NetworkResult.Loading->{


                }
            }
        })

    }
    }

    private fun setPieChart(active:Float,recovered:Float,deaths:Float ) {
        binding.pieChart.apply {
            this.setTouchEnabled(false)
            // Disable the description label
            description.isEnabled = false
            //Data for Pie Chart
            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(active, Constants.ACTIVE))
            entries.add(PieEntry(recovered, Constants.RECOVERED))
            entries.add(PieEntry(deaths, Constants.CASES))
            val dataSet = PieDataSet(entries, "")
            // Define custom colors for the Pie Chart
            val colors = mutableListOf<Int>()
            colors.add(ColorTemplate.rgb("#0000FF"))
            colors.add(ColorTemplate.rgb("#009900"))
            colors.add(ColorTemplate.rgb("#FF0000"))
            dataSet.colors = colors
//            dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
            val data = PieData(dataSet)
            setData(data)
// Customize the chart
            centerText = ""
//            setCenterTextSize(18f)
            //Animate on start
            animateY(1500, Easing.EaseInOutQuad)
// Show the labels on the right side
            dataSet.apply {
                setDrawValues(true)
                setDrawIcons(false)
                sliceSpace = 1f
                selectionShift = 5f
                //value line customization
                valueLinePart1OffsetPercentage = 90f
                valueLinePart2Length = 0.6f
                valueLineColor = android.R.color.transparent
                //value outside the chart
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                //disable labels
                setDrawEntryLabels(false)
                //Value Text Customization
                valueTextColor = Color.WHITE
                valueTextSize = 12f
//                valueTypeface =
//                    Typeface.createFromAsset(requireActivity().assets, "fonts/roboto.ttf")

                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }
            }


            //Disable center hole
            isDrawHoleEnabled = false
            //customize label text
            //            setDrawEntryLabels(false)
//            setEntryLabelColor(Color.BLACK)
//            setEntryLabelTextSize(12f)
//            setEntryLabelTypeface(Typeface.DEFAULT_BOLD)


            // Customize the legend
            legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                yEntrySpace = 10f
//                yOffset = -15f
                xOffset = 50f
                textSize = 12f
                textColor = Color.WHITE
//                typeface =
//                    Typeface.createFromAsset(requireActivity().assets, "fonts/roboto.ttf")
                formSize = 15f

            }
        }
    }

}