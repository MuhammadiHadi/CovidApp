package com.example.covid_19_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19_app.databinding.CountryListItemBinding
import com.example.covid_19_app.model.CountryListModelItem

class CountryListAdapter(private val itemList :List<CountryListModelItem>,
                         private val onItemClickListener: (position: Int) -> Unit)
    : RecyclerView.Adapter<CountryListAdapter.Holder>() {

    private var filteredItemList: List<CountryListModelItem> = itemList
    private lateinit var context: Context


    // Rest of the adapter code




    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : Holder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder : Holder , position : Int) {
        if (position != RecyclerView.NO_POSITION) {
            val countryList=filteredItemList[position]
            holder.bind(countryList)

        holder.itemView.setOnClickListener {
            val originalPosition = itemList.indexOf(countryList)
            onItemClickListener.invoke(originalPosition)
        }
        }
    }

    override fun getItemCount() : Int {
        return filteredItemList.size
    }
    fun filter(query: String) {
        filteredItemList = if (query.isBlank()) {
            itemList
        } else {
            itemList.filter { item ->
                // Apply your search logic here based on item properties
                item.country.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    inner class  Holder(private  val binding:CountryListItemBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(countryList :CountryListModelItem){
            binding.countryList=countryList
            binding.executePendingBindings()
        }

    }

}