package com.jessecorbett.diskord.api.common

import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public interface StickerItem {
    public val id: String
    public val name: String
    public val formatType: StickerFormat
}

@Serializable
public data class PartialSticker(
    @SerialName("id") override val id: String,
    @SerialName("name") override val name: String,
    @SerialName("format_type") override val formatType: StickerFormat,
) : StickerItem

@Serializable
public data class Sticker(
    @SerialName("id") override val id: String,
    @SerialName("pack_id") val packId: String? = null,
    @SerialName("name") override val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("tags") val tags: String,
    @SerialName("asset") val hash: String,
    @SerialName("type") val type: StickerType,
    @SerialName("format_type") override val formatType: StickerFormat,
    @SerialName("available") val available: Boolean? = null,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("sort_value") val sortValue: Int? = null,
) : StickerItem

@Serializable(with = StickerTypeSerializer::class)
public enum class StickerType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    STANDARD(1),
    GUILD(2),
}

public class StickerTypeSerializer : CodeEnumSerializer<StickerType>(StickerType.UNKNOWN, StickerType.values())

@Serializable(with = StickerFormatSerializer::class)
public enum class StickerFormat(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    PNG(1),
    APNG(2),
    LOTTIE(3)
}

public class StickerFormatSerializer : CodeEnumSerializer<StickerFormat>(StickerFormat.UNKNOWN, StickerFormat.values())

@Serializable
public data class StickerPack(
    @SerialName("id") val id: String,
    @SerialName("stickers") val stickers: List<Sticker>,
    @SerialName("name") val name: String,
    @SerialName("sku_id") val skuId: String,
    @SerialName("cover_sticker_id") val coverStickerId: String? = null,
    @SerialName("description") val description: String,
    @SerialName("banner_asset_id") val bannerAssetId: String,
)
