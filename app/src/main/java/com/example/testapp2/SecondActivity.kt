package com.example.testapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp2.VM.ActivityVM

class SecondActivity : AppCompatActivity() {

    private lateinit var activityVM: ActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        activityVM = ViewModelProvider(this).get(ActivityVM::class.java)

        Log.v("kishanlogs",activityVM.hashCode().toString())

        Log.v("kishanlogs",activityVM.getCounter().toString())

    }
}