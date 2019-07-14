package com.jessecorbett.diskord.util

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.thrownError
import kotlin.test.Test

class ColorsTest {
    @Test
    fun `should convert rgb floats to color`() {
        assertThat(Colors.rgb(1F, 0F, 0F)).isEqualTo(0xFF0000)
        assertThat(Colors.rgb(0F, 1F, 0F)).isEqualTo(0x00FF00)
        assertThat(Colors.rgb(0F, 0F, 1F)).isEqualTo(0x0000FF)
        assertThat(Colors.rgb(0F, 0F, 0F)).isEqualTo(0x000000)
    }

    @Test
    fun `should throw exception for out-of-range rgb floats`() {
        assertThat { Colors.rgb(-1F, 0F, 0F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(2F, 0F, 0F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0F, -1F, 0F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0F, 2F, 0F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0F, 0F, -1F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0F, 0F, 2F) }.isFailure().isInstanceOf(IllegalArgumentException::class)
    }

    @Test
    fun `should convert rgb integers to color`() {
        assertThat(Colors.rgb(255, 0, 0)).isEqualTo(0xFF0000)
        assertThat(Colors.rgb(0, 255, 0)).isEqualTo(0x00FF00)
        assertThat(Colors.rgb(0, 0, 255)).isEqualTo(0x0000FF)
        assertThat(Colors.rgb(0, 0, 0)).isEqualTo(0x000000)
    }

    @Test
    fun `should throw exception for out-of-range rgb integers`() {
        assertThat { Colors.rgb(256, 0, 0) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(-1, 0, 0) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0, 256, 0) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0, -1, 0) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0, 0, 256) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.rgb(0, 0, -1) }.isFailure().isInstanceOf(IllegalArgumentException::class)
    }

    @Test
    fun `should convert hex integer to color`() {
        assertThat(Colors.hex(0xFF0000)).isEqualTo(0xFF0000)
        assertThat(Colors.hex(0x00FF00)).isEqualTo(0x00FF00)
        assertThat(Colors.hex(0x0000FF)).isEqualTo(0x0000FF)
        assertThat(Colors.hex(0x000000)).isEqualTo(0x000000)
    }

    @Test
    fun `should convert hex string to color`() {
        assertThat(Colors.hex("#FF0000")).isEqualTo(0xFF0000)
        assertThat(Colors.hex("#00ff00")).isEqualTo(0x00FF00)
        assertThat(Colors.hex("#0000Ff")).isEqualTo(0x0000FF)
        assertThat(Colors.hex("#000000")).isEqualTo(0x000000)
    }

    @Test
    fun `should throw an exception for invalid hex strings`() {
        assertThat { Colors.hex("#F00") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("FF0000") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("red") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("#cchars") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("#eleven") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("#-10101") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("something #FFF000") }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex("#F00000 BAR") }.isFailure().isInstanceOf(IllegalArgumentException::class)
    }

    @Test
    fun `should throw exception for out-of-range hex values`() {
        assertThat { Colors.hex(0xF000000) }.isFailure().isInstanceOf(IllegalArgumentException::class)
        assertThat { Colors.hex(-0x1) }.isFailure().isInstanceOf(IllegalArgumentException::class)
    }

    @Test
    fun `should convert color to rgb triple`() {
        assertThat(Colors.toRGB(0xFF0000)).isEqualTo(Triple(255, 0, 0))
        assertThat(Colors.toRGB(0x00FF00)).isEqualTo(Triple(0, 255, 0))
        assertThat(Colors.toRGB(0x0000FF)).isEqualTo(Triple(0, 0, 255))
        assertThat(Colors.toRGB(0x000000)).isEqualTo(Triple(0, 0, 0))
    }
}
