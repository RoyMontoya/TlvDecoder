package com.example.roy.tvldecoder

import com.example.roy.tvldecoder.utils.HexConversion
import org.junit.Assert
import org.junit.Test

/**
 * Created by Roy on 2/20/18.
 */
class HexConversionTest {

    @Test
    fun convertBasicStringToAsciiTest() {
        //given
        val test = "74657374"
        val expectedResult = "test"
        //when
        val result = HexConversion.ToAscii(test)
        //assert
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun convertEmptyStringToAsciiReturnsEmptyTest() {
        //given
        val test = ""
        val expectedResult = ""
        //when
        val result = HexConversion.ToAscii(test)
        //assert
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun convertToAsciiRemovesWhiteSpacesTest() {
        //given      /TDC BLACK UNLIMITED VISA  /
        val test = "54444320424C41434B20554E4C494D4954454420564953412020"
        val expectedResult = "TDCBLACKUNLIMITEDVISA"
        //when
        val result = HexConversion.ToAscii(test)
        //assert
        Assert.assertEquals(expectedResult, result)
    }

}
