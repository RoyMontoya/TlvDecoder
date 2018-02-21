package com.example.roy.tvldecoder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

        linearLayoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = linearLayoutManager

        adapter = TlvAdapter(this, parseString(getString(R.string.second_example)))
        recyclerList.adapter = adapter

    }

}