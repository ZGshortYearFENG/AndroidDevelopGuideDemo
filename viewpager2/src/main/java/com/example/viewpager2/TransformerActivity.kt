package com.example.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_transformer.*

/**
 * ViewPager2 Transformer使用
 */
class TransformerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformer)

        viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return CardFragment()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transformer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.a -> viewpager2.setPageTransformer(mRotateAnimator)
            R.id.b -> viewpager2.setPageTransformer(mTranslateAnimator)
            R.id.c -> viewpager2.setPageTransformer(mScaleAnimator)
        }
        return super.onOptionsItemSelected(item)
    }

    private val mRotateAnimator = ViewPager2.PageTransformer { page, position ->
        page.rotation = position * 360
    }
    private val mTranslateAnimator = ViewPager2.PageTransformer { page, position ->
        page.translationY = Math.abs(position) * 500
    }
    private val mScaleAnimator = ViewPager2.PageTransformer { page, position ->
        page.scaleX = 1 - Math.abs(position)
        page.scaleY = 1 - Math.abs(position)
    }

}
