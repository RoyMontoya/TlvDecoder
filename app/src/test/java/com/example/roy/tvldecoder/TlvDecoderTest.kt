package com.example.roy.tvldecoder

import com.example.roy.tvldecoder.model.Tlv
import com.example.roy.tvldecoder.store.EMVTagStore
import com.example.roy.tvldecoder.utils.TlvDecoder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Roy on 2/20/18.
 */
class TlvDecoderTest {

    @Before
    fun setUp() {
        TlvDecoder.tlvList = mutableListOf<Tlv>()
    }

    @Test
    fun decodeOneTlvTagTest() {
        //given
        val stringToDecode = "5F201A54444320424C41434B20554E4C494D4954454420564953412020"
        //when
        val list = TlvDecoder.parseString(stringToDecode)
        //assert
        Assert.assertEquals(1, list.size)
    }

    @Test
    fun emptyStringDoesNotCreateTlvTagTest() {
        //given
        val stringToDecode = ""
        //when
        val list = TlvDecoder.parseString(stringToDecode)
        //assert
        Assert.assertEquals(0, list.size)
    }

    @Test
    fun decodeKnownTlvTest() {
        //given
        val stringToDecode = "5F201A54444320424C41434B20554E4C494D4954454420564953412020"
        val tagMeaning = EMVTagStore.emvMap.get(stringToDecode.substring(0, 4))?.name
        //when
        val list = TlvDecoder.parseString(stringToDecode)
        //assert
        Assert.assertEquals(tagMeaning, list.get(0).tagMeaning)
    }

    @Test
    fun decodeUnknownTlvTest() {
        //given
        val stringToDecode = "CC00"
        val tagMeaning = "Unknown Tag"
        //when
        val list = TlvDecoder.parseString(stringToDecode)
        //assert
        Assert.assertEquals(tagMeaning, list.get(0).tagMeaning)
    }

}