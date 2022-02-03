package com.jessecorbett.diskord.util

import com.jessecorbett.diskord.api.common.Color
import kotlin.math.roundToInt

/**
 * A collection of convenience methods and predefined colors for working with the color field in embeds.
 */
@Suppress("unused")
public object Colors {
    public const val INDIANRED: Int = 0xCD5C5C
    public const val LIGHTCORAL: Int = 0xF08080
    public const val SALMON: Int = 0xFA8072
    public const val DARKSALMON: Int = 0xE9967A
    public const val LIGHTSALMON: Int = 0xFFA07A
    public const val CRIMSON: Int = 0xDC143C
    public const val RED: Int = 0xFF0000
    public const val FIREBRICK: Int = 0xB22222
    public const val DARKRED: Int = 0x8B0000
    public const val PINK: Int = 0xFFC0CB
    public const val LIGHTPINK: Int = 0xFFB6C1
    public const val HOTPINK: Int = 0xFF69B4
    public const val DEEPPINK: Int = 0xFF1493
    public const val MEDIUMVIOLETRED: Int = 0xC71585
    public const val PALEVIOLETRED: Int = 0xDB7093
    public const val CORAL: Int = 0xFF7F50
    public const val TOMATO: Int = 0xFF6347
    public const val ORANGERED: Int = 0xFF4500
    public const val DARKORANGE: Int = 0xFF8C00
    public const val ORANGE: Int = 0xFFA500
    public const val GOLD: Int = 0xFFD700
    public const val YELLOW: Int = 0xFFFF00
    public const val LIGHTYELLOW: Int = 0xFFFFE0
    public const val LEMONCHIFFON: Int = 0xFFFACD
    public const val LIGHTGOLDENRODYELLOW: Int = 0xFAFAD2
    public const val PAPAYAWHIP: Int = 0xFFEFD5
    public const val MOCCASIN: Int = 0xFFE4B5
    public const val PEACHPUFF: Int = 0xFFDAB9
    public const val PALEGOLDENROD: Int = 0xEEE8AA
    public const val KHAKI: Int = 0xF0E68C
    public const val DARKKHAKI: Int = 0xBDB76B
    public const val LAVENDER: Int = 0xE6E6FA
    public const val THISTLE: Int = 0xD8BFD8
    public const val PLUM: Int = 0xDDA0DD
    public const val VIOLET: Int = 0xEE82EE
    public const val ORCHID: Int = 0xDA70D6
    public const val FUCHSIA: Int = 0xFF00FF
    public const val MAGENTA: Int = 0xFF00FF
    public const val MEDIUMORCHID: Int = 0xBA55D3
    public const val MEDIUMPURPLE: Int = 0x9370DB
    public const val REBECCAPURPLE: Int = 0x663399
    public const val BLUEVIOLET: Int = 0x8A2BE2
    public const val DARKVIOLET: Int = 0x9400D3
    public const val DARKORCHID: Int = 0x9932CC
    public const val DARKMAGENTA: Int = 0x8B008B
    public const val PURPLE: Int = 0x800080
    public const val INDIGO: Int = 0x4B0082
    public const val SLATEBLUE: Int = 0x6A5ACD
    public const val DARKSLATEBLUE: Int = 0x483D8B
    public const val MEDIUMSLATEBLUE: Int = 0x7B68EE
    public const val GREENYELLOW: Int = 0xADFF2F
    public const val CHARTREUSE: Int = 0x7FFF00
    public const val LAWNGREEN: Int = 0x7CFC00
    public const val LIME: Int = 0x00FF00
    public const val LIMEGREEN: Int = 0x32CD32
    public const val PALEGREEN: Int = 0x98FB98
    public const val LIGHTGREEN: Int = 0x90EE90
    public const val MEDIUMSPRINGGREEN: Int = 0x00FA9A
    public const val SPRINGGREEN: Int = 0x00FF7F
    public const val MEDIUMSEAGREEN: Int = 0x3CB371
    public const val SEAGREEN: Int = 0x2E8B57
    public const val FORESTGREEN: Int = 0x228B22
    public const val GREEN: Int = 0x008000
    public const val DARKGREEN: Int = 0x006400
    public const val YELLOWGREEN: Int = 0x9ACD32
    public const val OLIVEDRAB: Int = 0x6B8E23
    public const val OLIVE: Int = 0x808000
    public const val DARKOLIVEGREEN: Int = 0x556B2F
    public const val MEDIUMAQUAMARINE: Int = 0x66CDAA
    public const val DARKSEAGREEN: Int = 0x8FBC8B
    public const val LIGHTSEAGREEN: Int = 0x20B2AA
    public const val DARKCYAN: Int = 0x008B8B
    public const val TEAL: Int = 0x008080
    public const val AQUA: Int = 0x00FFFF
    public const val CYAN: Int = 0x00FFFF
    public const val LIGHTCYAN: Int = 0xE0FFFF
    public const val PALETURQUOISE: Int = 0xAFEEEE
    public const val AQUAMARINE: Int = 0x7FFFD4
    public const val TURQUOISE: Int = 0x40E0D0
    public const val MEDIUMTURQUOISE: Int = 0x48D1CC
    public const val DARKTURQUOISE: Int = 0x00CED1
    public const val CADETBLUE: Int = 0x5F9EA0
    public const val STEELBLUE: Int = 0x4682B4
    public const val LIGHTSTEELBLUE: Int = 0xB0C4DE
    public const val POWDERBLUE: Int = 0xB0E0E6
    public const val LIGHTBLUE: Int = 0xADD8E6
    public const val SKYBLUE: Int = 0x87CEEB
    public const val LIGHTSKYBLUE: Int = 0x87CEFA
    public const val DEEPSKYBLUE: Int = 0x00BFFF
    public const val DODGERBLUE: Int = 0x1E90FF
    public const val CORNFLOWERBLUE: Int = 0x6495ED
    public const val ROYALBLUE: Int = 0x4169E1
    public const val BLUE: Int = 0x0000FF
    public const val MEDIUMBLUE: Int = 0x0000CD
    public const val DARKBLUE: Int = 0x00008B
    public const val NAVY: Int = 0x000080
    public const val MIDNIGHTBLUE: Int = 0x191970
    public const val CORNSILK: Int = 0xFFF8DC
    public const val BLANCHEDALMOND: Int = 0xFFEBCD
    public const val BISQUE: Int = 0xFFE4C4
    public const val NAVAJOWHITE: Int = 0xFFDEAD
    public const val WHEAT: Int = 0xF5DEB3
    public const val BURLYWOOD: Int = 0xDEB887
    public const val TAN: Int = 0xD2B48C
    public const val ROSYBROWN: Int = 0xBC8F8F
    public const val SANDYBROWN: Int = 0xF4A460
    public const val GOLDENROD: Int = 0xDAA520
    public const val DARKGOLDENROD: Int = 0xB8860B
    public const val PERU: Int = 0xCD853F
    public const val CHOCOLATE: Int = 0xD2691E
    public const val SADDLEBROWN: Int = 0x8B4513
    public const val SIENNA: Int = 0xA0522D
    public const val BROWN: Int = 0xA52A2A
    public const val MAROON: Int = 0x800000
    public const val WHITE: Int = 0xFFFFFF
    public const val SNOW: Int = 0xFFFAFA
    public const val HONEYDEW: Int = 0xF0FFF0
    public const val MINTCREAM: Int = 0xF5FFFA
    public const val AZURE: Int = 0xF0FFFF
    public const val ALICEBLUE: Int = 0xF0F8FF
    public const val GHOSTWHITE: Int = 0xF8F8FF
    public const val WHITESMOKE: Int = 0xF5F5F5
    public const val SEASHELL: Int = 0xFFF5EE
    public const val BEIGE: Int = 0xF5F5DC
    public const val OLDLACE: Int = 0xFDF5E6
    public const val FLORALWHITE: Int = 0xFFFAF0
    public const val IVORY: Int = 0xFFFFF0
    public const val ANTIQUEWHITE: Int = 0xFAEBD7
    public const val LINEN: Int = 0xFAF0E6
    public const val LAVENDERBLUSH: Int = 0xFFF0F5
    public const val MISTYROSE: Int = 0xFFE4E1
    public const val GAINSBORO: Int = 0xDCDCDC
    public const val LIGHTGRAY: Int = 0xD3D3D3
    public const val SILVER: Int = 0xC0C0C0
    public const val DARKGRAY: Int = 0xA9A9A9
    public const val GRAY: Int = 0x808080
    public const val DIMGRAY: Int = 0x696969
    public const val LIGHTSLATEGRAY: Int = 0x778899
    public const val SLATEGRAY: Int = 0x708090
    public const val DARKSLATEGRAY: Int = 0x2F4F4F
    public const val BLACK: Int = 0x000000

    /**
     * Creates a [Color] instance which represents the specified red, green, and blue values.
     *
     * Each specified value should be between 0.0 and 1.0.
     *
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     */
    public fun rgb(red: Double, green: Double, blue: Double): Color = rgb(red.toFloat(), green.toFloat(), blue.toFloat())

    /**
     * Creates a [Color] instance which represents the specified red, green, and blue values.
     *
     * Each specified value should be between 0.0 and 1.0.
     *
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     */
    public fun rgb(red: Float, green: Float, blue: Float): Color {
        require(red in 0.0F..1.0F) { "$red is not between 0 and 1." }
        require(green in 0.0F..1.0F) { "$green is not between 0 and 1." }
        require(blue in 0.0F..1.0F) { "$blue is not between 0 and 1." }

        // Use roundToInt instead of toInt, as toInt truncates the float 254.745 (an input of 0.999) to 254, when it should be 255.
        return rgb((red * 255).roundToInt(), (green * 255).roundToInt(), (blue * 255).roundToInt())
    }

    /**
     * Creates a [Color] instance which represents the specified red, green, and blue values.
     *
     * Each specified value should be between 0 and 255.
     *
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     */
    public fun rgb(red: Int, green: Int, blue: Int): Color {
        require(red in 0..255) { "$red is not between 0 and 255." }
        require(green in 0..255) { "$green is not between 0 and 255." }
        require(blue in 0..255) { "$blue is not between 0 and 255." }

        return (red and 0xFF shl 16) or (green and 0xFF shl 8) or (blue and 0xFF)
    }

    /**
     * Creates a [Color] instance which represents the specified hex value.
     *
     * @param hex The integer value.
     */
    public fun hex(hex: Int): Color {
        require(hex in 0..0xFFFFFF) { "$hex is not between 0x000000 and 0xFFFFFF." }

        return hex
    }

    /**
     * Creates a [Color] instance which represents the specified hex string (e.g. `#FF0000`).
     *
     * @param hex The hex string.
     */
    public fun hex(hex: String): Color {
        require(hex matches "#[0-9A-F]{6}".toRegex(RegexOption.IGNORE_CASE)) {
            "$hex is not a valid hex string between #000000 and #FFFFFF."
        }

        return hex(hex.drop(1).toInt(16))
    }

    /**
     * Converts a [Color] instance into a [kotlin.Triple] containing the `red`, `green`, and `blue` values of the color.
     *
     * @param color The color to convert.
     *
     * @return A Triple of the color values in RGB order.
     */
    public fun toRGB(color: Color): Triple<Int, Int, Int> = Triple(color shr 16 and 0xFF, color shr 8 and 0xFF, color and 0xFF)
}
