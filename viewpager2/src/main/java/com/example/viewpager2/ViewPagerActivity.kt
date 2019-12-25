package com.example.viewpager2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_view_pager.*

/**
 * ViewPager的Fragment生命周期观察
 */
class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        /**
         * FragmentPagerAdapter 适合fragment数据量小的，会给fragment在内存中存一份（调用一次onAttach onCreate下次直接走onCreateView，销毁走onPause onStop onDestroyView）
         * FragmentStatePagerAdapter 适合fragment多的情况，顾名思义就是在fragment销毁的时候保存fragment的state，不会在内存保存，(销毁走onPause onStop onDestroyView onDestroy onDetach)
         *
         * 与ViewPager2的FragmentStateAdapter对比
         * FragmentStateAdapter，看名字有State，所以看得出也是内存不保存直接销毁，但是由于ViewPager2基于recyclerview，所以会缓存两个fragment，并且默认不预加载
         */
        viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return CardFragment()
            }

            override fun getCount(): Int {
                return 5
            }
        }
    }
}