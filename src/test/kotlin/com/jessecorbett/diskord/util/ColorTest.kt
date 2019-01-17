package com.jessecorbett.diskord.util

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

class ColorTest {
    @Test
    fun `should convert rgb floats to color`() {
        assert(Colors.rgb(1F, 0F, 0F)).isEqualTo(0xFF0000)
        assert(Colors.rgb(0F, 1F, 0F)).isEqualTo(0x00FF00)
        assert(Colors.rgb(0F, 0F, 1F)).isEqualTo(0x0000FF)
        assert(Colors.rgb(0F, 0F, 0F)).isEqualTo(0x000000)
    }

    @Test
    fun `should throw exception for out-of-range rgb floats`() {
        assert { Colors.rgb(-1F, 0F, 0F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(2F, 0F, 0F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0F, -1F, 0F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0F, 2F, 0F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0F, 0F, -1F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0F, 0F, 2F) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
    }

    @Test
    fun `should convert rgb integers to color`() {
        assert(Colors.rgb(255, 0, 0)).isEqualTo(0xFF0000)
        assert(Colors.rgb(0, 255, 0)).isEqualTo(0x00FF00)
        assert(Colors.rgb(0, 0, 255)).isEqualTo(0x0000FF)
        assert(Colors.rgb(0, 0, 0)).isEqualTo(0x000000)
    }

    @Test
    fun `should throw exception for out-of-range rgb integers`() {
        assert { Colors.rgb(256, 0, 0) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(-1, 0, 0) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0, 256, 0) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0, -1, 0) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0, 0, 256) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.rgb(0, 0, -1) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
    }

    @Test
    fun `should convert hex integer to color`() {
        assert(Colors.hex(0xFF0000)).isEqualTo(0xFF0000)
        assert(Colors.hex(0x00FF00)).isEqualTo(0x00FF00)
        assert(Colors.hex(0x0000FF)).isEqualTo(0x0000FF)
        assert(Colors.hex(0x000000)).isEqualTo(0x000000)
    }

    @Test
    fun `should convert hex string to color`() {
        assert(Colors.hex("#FF0000")).isEqualTo(0xFF0000)
        assert(Colors.hex("#00ff00")).isEqualTo(0x00FF00)
        assert(Colors.hex("#0000Ff")).isEqualTo(0x0000FF)
        assert(Colors.hex("#000000")).isEqualTo(0x000000)
    }

    @Test
    fun `should throw an exception for invalid hex strings`() {
        assert { Colors.hex("#F00") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("FF0000") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("red") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("#cchars") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("#eleven") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("#-10101") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("something #FFF000") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex("#F00000 BAR") }.thrownError { isInstanceOf(IllegalArgumentException::class) }
    }

    @Test
    fun `should throw exception for out-of-range hex values`() {
        assert { Colors.hex(0xF000000) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
        assert { Colors.hex(-0x1) }.thrownError { isInstanceOf(IllegalArgumentException::class) }
    }

    @Test
    fun `should convert color to rgb triple`() {
        assert(Colors.toRGB(0xFF0000)).isEqualTo(Triple(255, 0, 0))
        assert(Colors.toRGB(0x00FF00)).isEqualTo(Triple(0, 255, 0))
        assert(Colors.toRGB(0x0000FF)).isEqualTo(Triple(0, 0, 255))
        assert(Colors.toRGB(0x000000)).isEqualTo(Triple(0, 0, 0))
    }
}
