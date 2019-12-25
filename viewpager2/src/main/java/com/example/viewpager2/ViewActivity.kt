package com.example.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        viewpager2.adapter = CardViewAdapter()
    }


}

/**
 * Viewpager2父类是ViewGroup，用RecyclerView展示数据，所以展示view布局用RecyclerView.Adapter作为viewpager2的adapter
 * FragmentStateAdapter同样也是继承RecyclerView.Adapter
 * FragmentStateAdapter.onCreateViewHolder 调用FragmentViewHolder.create加载一个FrameLayout作为Fragment的载体
 * onBindViewHolder 先判断显示的fragment是不是正确的fragment，否则移除，然后走placeFragmentInViewHolder方法插入对应的fragment
 */
class CardViewAdapter : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 5;
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

    }
}

class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}
