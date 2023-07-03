package com.example.covid_19_app.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.example.covid_19_app.R

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(image:String){
    // coil library code
    this.load(image){
        crossfade(true)
        crossfade(400)
        placeholder(R.drawable.placeholder)
    }
}