package com.example.testapp2.VM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityVM : ViewModel() {

   private var counter =0

    fun increment (){
        counter++
    }

    fun getCounter() : Int{
        return counter
    }

}