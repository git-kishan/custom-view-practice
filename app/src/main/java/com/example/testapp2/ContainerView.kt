package com.example.testapp2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.testapp2.databinding.BoxBinding
import com.example.testapp2.databinding.ContainerLayoutBinding

class ContainerView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var binding: ContainerLayoutBinding

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.container_layout,
            this,
            true)
    }

    fun inflateItems(count : Int,list:List<String>){
        (1..count).forEach{
            val childView = addView()
            binding.llLayout.addView(childView)
        }
    }

    private fun addView() : View {
        val viewBinding = DataBindingUtil.inflate<BoxBinding>(
            LayoutInflater.from(context),
            R.layout.box,
            null,
            false
        )
        return viewBinding.root
    }
}