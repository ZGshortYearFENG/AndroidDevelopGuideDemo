package com.example.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_carousel.*

class CarouselActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)

        viewpager2.adapter = object : FragmentStateAdapter(this) {

            private val data = arrayListOf<String>("11", "22", "33")
            private val looperCount = 500
            private fun getRealCount() = data.size

            override fun getItemCount(): Int {
                return looperCount * getRealCount()
            }

            override fun createFragment(position: Int): Fragment {
                return CardFragment(data[position % getRealCount()])
            }
        }
    }
}
