package com.example.roy.tvldecoder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.example.roy.tvldecoder.R
import com.example.roy.tvldecoder.adapters.TlvAdapter
import com.example.roy.tvldecoder.utils.TlvDecoder.Companion.parseString

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TlvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerList: RecyclerView = findViewById(R.id.recyclerView)
        val buttonLayout: LinearLayout = findViewById(R.id.layout_buttons)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = linearLayoutManager

        var buttonExample1: Button = findViewById(R.id.button_first_example)
        buttonExample1.setOnClickListener {
            buttonLayout.visibility = View.GONE
            adapter = TlvAdapter(this, parseString(getString(R.string.first_test)))
            recyclerList.adapter = adapter
        }

        var buttonExample2: Button = findViewById(R.id.button_second_example)
        buttonExample2.setOnClickListener {
            buttonLayout.visibility = View.GONE
            adapter = TlvAdapter(this, parseString(getString(R.string.second_example)))
            recyclerList.adapter = adapter
        }

    }

}