package com.example.roy.tvldecoder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val mediumTagLength = 4
    val smallTagLength = 2
    val lengthTagLength = 2

    var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()
    var convertedString: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseString(getString(R.string.second_example))

        tlvList.size
    }

    fun parseString(stringToDecode: String) {
        var longString = stringToDecode
        val mediumTag = longString.substring(0..3)
        val smallTag = longString.substring(0..1)
        var tlv: Tlv?

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
            tlvList.add(Tlv("", "Failed to Parse", longString, 0))
        }

    }


    fun createTlv(stringToParse: String, tag: String, tagLength: Int): Tlv {
        val remainingString = stringToParse.substring(tagLength)
        val emvTag = EMVTagStore.emvMap.get(tag)
        val tlvValue = remainingString.substring(2, getLength(remainingString))

        return Tlv(tag, emvTag?.name ?: "Unknown Tag",
                if(emvTag?.encoded?: false) hexToAscii(tlvValue) else tlvValue, tlvValue.length)
    }

    fun getLength(remainingString: String): Int = (transformHexToInt(remainingString.substring(0..1))) + lengthTagLength


    fun transformHexToInt(hexValue: String): Int = hexValue.toInt(radix = 16) * 2

    fun hexToAscii(hexStr: String): String {
        val output = StringBuilder("")
        var i = 0
        while (i < hexStr.length) {
            val str = hexStr.substring(i, i + 2)
            output.append(Integer.parseInt(str, 16).toChar())
            i += 2
        }
        return output.toString()
    }


}
