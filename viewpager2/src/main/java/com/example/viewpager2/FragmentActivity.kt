package com.example.viewpager2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return CardFragment()
            }
        }
    }
}

/**
 * Fragment生命周期观察
 * 默认缓存2个Fragment
 */

class CardFragment(var text: String = "textView") : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fzjtest", "onCreateView")
        return LayoutInflater.from(context).inflate(R.layout.item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("fzjtest", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fzjtest", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.textView).setText(text)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("fzjtest", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("fzjtest", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("fzjtest", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("fzjtest", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("fzjtest", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("fzjtest", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fzjtest", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fzjtest", "onDetach")
    }
}
