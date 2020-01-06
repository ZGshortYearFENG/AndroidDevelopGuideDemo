package com.example.opengl

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = SimpleAdapter(
            this,
            getData(),
            android.R.layout.simple_list_item_1,
            arrayOf("title"),
            intArrayOf(android.R.id.text1)
        )
    }

    private fun getData(): List<Map<String, Any>> {
        val data = mutableListOf<Map<String, Any>>()

        data.add(
            mapOf(
                "title" to "Triangle",
                "intent" to Intent(this, TriangleActivity::class.java)
            )
        )
        data.add(
            mapOf(
                "title" to "Square",
                "intent" to Intent(this, SquareActivity::class.java)
            )
        )
        data.add(
            mapOf(
                "title" to "Texture",
                "intent" to Intent(this, TextureActivity::class.java)
            )
        )
        return data
    }

    override fun onListItemClick(listView: ListView, view: View, position: Int, id: Long) {
        val map = listView.getItemAtPosition(position) as Map<*, *>
        val intent = Intent(map["intent"] as Intent)
        startActivity(intent)
    }
}
