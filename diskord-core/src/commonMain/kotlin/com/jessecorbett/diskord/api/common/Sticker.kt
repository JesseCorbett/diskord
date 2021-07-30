package com.jessecorbett.diskord.api.common

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

@Serializable
public enum class StickerType {
    @SerialName("1") STANDARD,
    @SerialName("2") GUILD,
}

@Serializable
public enum class StickerFormat {
    @SerialName("1") PNG,
    @SerialName("2") APNG,
    @SerialName("3") LOTTIE
}

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
