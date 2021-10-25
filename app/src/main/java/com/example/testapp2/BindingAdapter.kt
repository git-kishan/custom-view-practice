package com.example.testapp2

import androidx.databinding.BindingAdapter


    @BindingAdapter("boxCount","listCount",requireAll = false)
    fun bindBox(view : ContainerView , boxCount : Int,listCount : List<String>){
        view.inflateItems(boxCount,listCount)
    }
