package com.example.roy.tvldecoder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val mediumTagLength = 4
    val smallTagLength = 2
    val lengthTagLength = 2

    var tlvList: MutableList<Tlv> = mutableListOf<Tlv>()
    var convertedString : String = ""


    val emvMap: HashMap<String, EMVTag> = hashMapOf(
            "5F20" to EMVTag("Cardholder Name", true),
            "4F" to EMVTag("Application Identifier", false),
            "5F24" to EMVTag("Application Expiration Date", false),
            "9F16" to EMVTag("Merchant Identifier", true),
            "9F21" to EMVTag("Transaction Time", false),
            "9A" to EMVTag("Transaction Date", false),
            "9F02" to EMVTag("Amount, Authorised (Numeric)", false),
            "9F03" to EMVTag("Amount, Other (Numeric)", false),
            "9F34" to EMVTag("Cardholder Verification Method (CVM) Results", false),
            "9F12" to EMVTag("Application Preferred Name", true),
            "9F06" to EMVTag("Application Identifier (AID) – terminal", false),
            "5F30" to EMVTag("Service Code", false),
            "9F4E" to EMVTag("Merchant Name and Location", true),
            "5A" to EMVTag("Application Primary Account Number (PAN)", false),
            "57" to EMVTag("Track 2 Equivalent Data", false),
            "9F10" to EMVTag("Issuer Application Data", false),
            "82" to EMVTag("Application Interchange Profile", false),
            "8E" to EMVTag("Cardholder Verification Method (CVM) List", false),
            "5F25" to EMVTag("Application Effective Date", false),
            "9F07" to EMVTag("Application Usage Control", false),
            "9F0D" to EMVTag("Issuer Action Code Default", false),
            "9F0E" to EMVTag("Issuer Action Code Denial", false),
            "9F0F" to EMVTag("Issuer Action Code Online", false),
            "9F26" to EMVTag("Application Cryptogram", false),
            "9F27" to EMVTag("Cryptogram Information Data", false),
            "9F36" to EMVTag(" Application Transaction Counter (ATC)", false),
            "9C" to EMVTag("Transaction Type", false),
            "9F33" to EMVTag("Terminal Capabilities", false),
            "9F37" to EMVTag("Unpredictable Number", false),
            "9F39" to EMVTag("Point-of-Service (POS) Entry Mode", false),
            "9F40" to EMVTag("Additional Terminal Capabilities", false),
            "95" to EMVTag("Terminal Verification Results", false),
            "9B" to EMVTag("Transaction Status Information", false),
            "84" to EMVTag("Dedicated File (DF) Name", false),
            "5F2A" to EMVTag("Transaction Currency Code", false),
            "5F34" to EMVTag("Application Primary Account Number (PAN) Sequence Number", false),
            "9F09" to EMVTag("Application Version Number", false),
            "9F1A" to EMVTag("Terminal Country Code", false),
            "9F1E" to EMVTag("Interface Device (IFD) Serial Number", true),
            "9F35" to EMVTag("Terminal Type", false),
            "9F41" to EMVTag("Transaction Sequence Counter", false),
            "5F28" to EMVTag("Issuer Country Code", false),
            "9F53" to EMVTag("Unknown tag", false),
            "8A" to EMVTag("Application Label", true),
            "50" to EMVTag("Authorisation Response Code", false),
            "9F08" to EMVTag("Application Version Number", false))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        parseString(getString(R.string.second_example))

        convertedString = hexToAscii("54444320424C41434B20554E4C494D49544544205649534120")

        print(convertedString)
    }

    fun parseString(stringToDecode: String) {
        var longString = stringToDecode
        val mediumTag = longString.substring(0..3)
        val smallTag = longString.substring(0..1)
        var tlv: Tlv?

        if (emvMap.containsKey(mediumTag)) {
            tlv = createTlv(longString, mediumTag, mediumTagLength)
            longString = longString.substring(mediumTagLength + lengthTagLength + tlv.value.length)
            tlvList.add(tlv)
        } else {
            tlv = createTlv(longString, smallTag, smallTagLength)
            longString = longString.substring(smallTagLength + lengthTagLength + tlv.value.length)
            tlvList.add(tlv)
        }
        if (longString.isNotEmpty() && longString.length > 3) {
            parseString(longString)
        } else {
           tlvList.add(Tlv("", "Failed to Parse", longString))
        }


    }


    fun createTlv(stringToParse: String, tag: String, tagLength: Int): Tlv {
        val remainingString = stringToParse.substring(tagLength)

        return Tlv(tag, emvMap.get(tag)?.name ?: "Unknown Tag",
                remainingString.substring(2, getLength(remainingString)))
    }

    fun getLength(remainingString: String): Int = (transformHexToInt(remainingString.substring(0..1))) + lengthTagLength


    fun transformHexToInt(hexValue: String): Int = hexValue.toInt(radix = 16) * 2

    fun hexToAscii(hexStr:String):String {
        val output = StringBuilder("")
        var i = 0
        while (i < hexStr.length)
        {
            val str = hexStr.substring(i, i + 2)
            output.append(Integer.parseInt(str, 16).toChar())
            i += 2
        }
        return output.toString()
    }


}
