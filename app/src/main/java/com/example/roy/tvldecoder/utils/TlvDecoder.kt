package com.example.roy.tvldecoder.utils

import com.example.roy.tvldecoder.model.Tlv
import com.example.roy.tvldecoder.store.EMVTagStore

/**
 * Created by Roy on 2/20/18.
 */
class TlvDecoder {

    companion object {

        val lengthTagLength = 2
        var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()

        fun parseString(stringToDecode: String): MutableList<Tlv> {
            if (stringToDecode.isEmpty()) return tlvList
            if (stringToDecode.length < 4) {
                tlvList.add(createFailedToParseTlvTag(stringToDecode))
                return tlvList
            }
            var stringToParse = stringToDecode
            val mediumTag = stringToParse.substring(0..3)
            val smallTag = stringToParse.substring(0..1)
            val tlv: Tlv

            tlv = if (EMVTagStore.emvMap.containsKey(mediumTag)) createTlv(stringToParse, mediumTag)
            else createTlv(stringToParse, smallTag)

            stringToParse = stringToParse.substring(tlv.tag.length + lengthTagLength + tlv.hexValueLength)
            tlvList.add(tlv)

            if (stringToParse.isNotEmpty()) parseString(stringToParse)

            return tlvList
        }

        private fun createTlv(stringToParse: String, tag: String): Tlv {
            val remainingString = stringToParse.substring(tag.length)
            val emvTag = EMVTagStore.emvMap.get(tag)
            val tlvValue = remainingString.substring(2, getValueLength(remainingString))

            return Tlv(tag, emvTag?.name ?: "Unknown Tag",
                    if (emvTag?.encoded ?: false) HexConversion.ToAscii(tlvValue) else tlvValue,
                    tlvValue.length)
        }

        private fun getValueLength(remainingString: String): Int =
                HexConversion.ToInt(remainingString.substring(0..1)) + lengthTagLength

        private fun createFailedToParseTlvTag(stringToParse: String): Tlv {
            return Tlv("", "Failed to Parse",
                    stringToParse, 0)
        }

        private fun isLengthValid(stringToParse: String): Boolean {
            if (stringToParse.length >= 2) {
                val length = HexConversion.ToInt(stringToParse.substring(0, 2))
                return (length >= stringToParse.length - 2)
            }
            return false
        }

    }

}