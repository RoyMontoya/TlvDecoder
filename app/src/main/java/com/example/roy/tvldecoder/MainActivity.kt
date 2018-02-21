package com.example.roy.tvldecoder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val mediumTagLength = 4
    val smallTagLength = 2
    val lengthTagLength = 2

    var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TlvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerList: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = linearLayoutManager

        parseString(getString(R.string.first_test))

        adapter = TlvAdapter(this, tlvList)
        recyclerList.adapter = adapter

    }

    fun parseString(stringToDecode: String) {
        var longString = stringToDecode
        val mediumTag = longString.substring(0..3)
        val smallTag = longString.substring(0..1)
        val tlv: Tlv?

        if (EMVTagStore.emvMap.containsKey(mediumTag)) {
            tlv = createTlv(longString, mediumTag, mediumTagLength)
            longString = longString.substring(mediumTagLength + lengthTagLength + tlv.originalLength)
            tlvList.add(tlv)
        } else {
            tlv = createTlv(longString, smallTag, smallTagLength)
            longString = longString.substring(smallTagLength + lengthTagLength + tlv.originalLength)
            tlvList.add(tlv)
        }
        if (longString.isNotEmpty() && longString.length > 3) {
            parseString(longString)
        } else {
            tlvList.add(Tlv("", "Failed to Parse", longString, longString.length))
        }

    }

    fun createTlv(stringToParse: String, tag: String, tagLength: Int): Tlv {
        val remainingString = stringToParse.substring(tagLength)
        val emvTag = EMVTagStore.emvMap.get(tag)
        val tlvValue = remainingString.substring(2, getValueLength(remainingString))

        return Tlv(tag, emvTag?.name ?: "Unknown Tag",
                if (emvTag?.encoded ?: false) HexConversion.ToAscii(tlvValue) else tlvValue, tlvValue.length)
    }

    fun getValueLength(remainingString: String): Int = (HexConversion.ToInt(remainingString.substring(0..1))) + lengthTagLength

}
