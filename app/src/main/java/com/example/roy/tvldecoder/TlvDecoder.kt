package com.example.roy.tvldecoder

/**
 * Created by Roy on 2/20/18.
 */
class TlvDecoder {

    companion object {

        val lengthTagLength = 2
        var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()

        fun parseString(stringToDecode: String): MutableList<Tlv> {
            if (stringToDecode.isEmpty()) return tlvList
            var stringToParse = stringToDecode
            val mediumTag = stringToParse.substring(0..3)
            val smallTag = stringToParse.substring(0..1)
            val tlv: Tlv

            if (EMVTagStore.emvMap.containsKey(mediumTag)) {
                tlv = createTlv(stringToParse, mediumTag)
                stringToParse = stringToParse.substring(mediumTag.length + lengthTagLength + tlv.hexValueLength)
            } else {
                tlv = createTlv(stringToParse, smallTag)
                stringToParse = stringToParse.substring(smallTag.length + lengthTagLength + tlv.hexValueLength)
            }
            tlvList.add(tlv)


            if (stringToParse.isNotEmpty() && stringToParse.length > 3) {
                parseString(stringToParse)
            } else {
                tlvList.add(Tlv("", "Failed to Parse",
                        stringToParse, 0))
            }

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

    }

}