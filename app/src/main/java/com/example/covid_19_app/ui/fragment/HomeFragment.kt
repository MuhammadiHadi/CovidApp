package com.example.covid_19_app.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
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
import com.example.covid_19_app.utils.RoundedBarChartRenderer
import com.example.covid_19_app.viewModel.AllDataVMFactory
import com.example.covid_19_app.viewModel.AllDataViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.Utils


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
                    setBarChart(it.data.active,it.data.cases,it.data.recovered,it.data.deaths)
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
            colors.add(ColorTemplate.rgb("#AB784E"))
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
/*
typeface =
Typeface.createFromAsset(requireActivity().assets, "fonts/roboto.ttf")
*/
                formSize = 15f

            }
        }
    }
    private fun setBarChart(active:Float,cases:Float,recovered:Float,deaths:Float) {
        // Create a list of colors to represent the color of each bar
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.BLUE)
        colors.add(ColorTemplate.rgb("#AB784E"))
        colors.add(Color.GREEN)
        colors.add(Color.RED)

        //Bar Values
        val entries: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, active))
        entries.add(BarEntry(1f, cases))
        entries.add(BarEntry(2f, recovered))
        entries.add(BarEntry(3f, deaths))


        val dataSet = BarDataSet(entries, "bar Chart")
        dataSet.apply {
            this.colors = colors
            valueTextColor = Color.WHITE
            valueTextSize = 11f
//            valueTypeface =
//                Typeface.createFromAsset(requireActivity().assets, "fonts/roboto_medium.ttf")
            setDrawValues(true)
            // set custom value formatter that returns labels to display on top of bars
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${value.toInt()}"
                }
            }
        }


        // Create custom renderer with 4dp corner radius
        val roundedRenderer = RoundedBarChartRenderer(
            binding.idBarChart,
            binding.idBarChart.animator,
            binding.idBarChart.viewPortHandler,
            Utils.convertDpToPixel(4f)
        )
        val barData = BarData(dataSet)

        binding.idBarChart.apply {
            renderer = roundedRenderer
            data = barData
            setDrawValueAboveBar(true)
            xAxis.setDrawGridLines(false)
            setTouchEnabled(false)
            axisRight.setDrawLabels(false)
            axisLeft.setDrawLabels(false)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            setDrawBorders(false)
            description.isEnabled = false
            legend.isEnabled = false
            barData.setDrawValues(true) }
        val labels = arrayOf("active", "cases", "recovered", "deaths")
        val xAxis = binding.idBarChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
//            setCenterAxisLabels(true)
            xAxis.mLabelWidth = barData.barWidth.toInt() // set the bar width as label width
            granularity = 1f
//            isGranularityEnabled = false
            labelCount = labels.size
            valueFormatter = IndexAxisValueFormatter(labels)
            setAvoidFirstLastClipping(false)
            textColor = Color.WHITE
            textSize = 11f
//            typeface = Typeface.createFromAsset(requireActivity().assets, "fonts/roboto_medium.ttf")
        }
        binding.idBarChart.invalidate()

    }

}