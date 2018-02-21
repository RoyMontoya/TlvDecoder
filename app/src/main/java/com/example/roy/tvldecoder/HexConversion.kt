package com.example.roy.tvldecoder

/**
 * Created by Roy on 2/19/18.
 */
class HexConversion {

    companion object {

        fun ToInt(hexValue: String): Int = hexValue.toInt(radix = 16) * 2

        fun ToAscii(hexStr: String): String {
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


}