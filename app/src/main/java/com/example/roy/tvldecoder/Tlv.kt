package com.example.roy.tvldecoder

/**
 * Created by Roy on 2/18/18.
 */
data class Tlv(val tag: String, val tagMeaning: String?, val value: String, val originalLength: Int)