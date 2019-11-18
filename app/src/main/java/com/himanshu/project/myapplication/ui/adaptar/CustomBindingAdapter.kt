package com.himanshu.project.myapplication.ui.adaptar

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.himanshu.project.myapplication.R

object CustomBindingAdapter {

    @BindingAdapter("urlToImage")
    @JvmStatic
    fun urlToImage(view: ImageView, urlToImage: String?) {
        Glide.with(view.context)
            .load(urlToImage)
            .placeholder(R.drawable.swivel_logo)
            .centerCrop()
            .into(view)
    }

    /* @BindingAdapter("entries")
     @JvmStatic
     fun setEntries(spinner : AppCompatSpinner, entries: Array<String>?) {
         var app : Application = Swivel.applicationContext() as Application
         val aa = entries?.let { ArrayAdapter(app, android.R.layout.simple_spinner_item, it) }
          aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         spinner.adapter = aa
     }*/

}