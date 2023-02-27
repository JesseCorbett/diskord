package com.jessecorbett.diskord.api.interaction

import com.jessecorbett.diskord.api.common.Attachment
import com.jessecorbett.diskord.api.common.GuildMember
import com.jessecorbett.diskord.api.common.Message
import com.jessecorbett.diskord.api.common.PartialChannel
import com.jessecorbett.diskord.api.common.PartialMember
import com.jessecorbett.diskord.api.common.Role
import com.jessecorbett.diskord.api.common.SelectOption
import com.jessecorbett.diskord.api.common.User
import com.jessecorbett.diskord.api.interaction.command.CommandType
import com.jessecorbett.diskord.internal.CodeEnum
import com.jessecorbett.diskord.internal.CodeEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
public sealed class Interaction {
    public abstract val id: String
    public abstract val applicationId: String
    public abstract val token: String
    public abstract val version: Int
    public abstract val appPermissions: String?
}

@Serializable(with = InteractionTypeSerializer::class)
public enum class InteractionType(public override val code: Int) : CodeEnum {
    UNKNOWN(-1),
    Ping(1),
    ApplicationCommand(2),
    MessageComponent(3),
    AutocompletePrompt(4)
}

public class InteractionTypeSerializer : CodeEnumSerializer<InteractionType>(InteractionType.UNKNOWN, InteractionType.values())

@Serializable
@SerialName("1")
public data class InteractionPing(
    @SerialName("id") override val id: String,
    @SerialName("application_id") override val applicationId: String,
    @SerialName("token") override val token: String,
    @SerialName("version") override val version: Int = 1,
    @SerialName("app_permissions") override val appPermissions: String? = null
) : Interaction()

@Serializable
@SerialName("2")
public data class ApplicationCommand(
    @SerialName("id") override val id: String,
    @SerialName("application_id") override val applicationId: String,
    @SerialName("token") override val token: String,
    @SerialName("version") override val version: Int,
    @SerialName("app_permissions") override val appPermissions: String? = null,
    @SerialName("data") val data: Data,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("member") val member: GuildMember? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("message") val message: Message? = null,
    @SerialName("locale") val userLocale: String? = null,
    @SerialName("guild_locale") val guildLocale: String? = null
) : Interaction() {
    @Serializable
    @JsonClassDiscriminator("type")
    public sealed class Data {
        public abstract val commandId: String
        public abstract val commandName: String
    }

    @Serializable
    @SerialName("1")
    public data class ChatData(
        @SerialName("id") override val commandId: String,
        @SerialName("name") override val commandName: String,
        @SerialName("resolved") public val resolvedResources: CommandInteractionDataResolved? = null,
        @SerialName("options") public val options: List<CommandInteractionOptionResponse> = emptyList()
    ) : Data()

    @Serializable
    @SerialName("2")
    public data class UserData(
        @SerialName("id") override val commandId: String,
        @SerialName("name") override val commandName: String,
        @SerialName("resolved") public val convertedUsersRolesChannels: CommandInteractionDataResolved? = null,
    ) : Data()

    @Serializable
    @SerialName("3")
    public data class MessageData(
        @SerialName("id") override val commandId: String,
        @SerialName("name") override val commandName: String,
        @SerialName("resolved") public val convertedUsersRolesChannels: CommandInteractionDataResolved? = null,
    ) : Data()
}

@Serializable
@SerialName("3")
public data class MessageComponent(
    @SerialName("id") override val id: String,
    @SerialName("application_id") override val applicationId: String,
    @SerialName("data") val data: Data,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("member") val member: GuildMember? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("token") override val token: String,
    @SerialName("version") override val version: Int,
    @SerialName("app_permissions") override val appPermissions: String? = null,
    @SerialName("message") val message: Message? = null,
    @SerialName("locale") val userLocale: String? = null,
    @SerialName("guild_locale") val guildLocale: String? = null
) : Interaction() {
    @Serializable
    public data class Data(
        @SerialName("custom_id") public val customId: String,
        @SerialName("component_type") public val type: String, // TODO: Can this be a sealed class as well?
        @SerialName("values") public val values: List<SelectOption>
    )
}

@Serializable
@SerialName("4")
public data class AutocompletePrompt(
    @SerialName("id") override val id: String,
    @SerialName("application_id") override val applicationId: String,
    @SerialName("data") val data: CommandInteractionData? = null,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("member") val member: GuildMember? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("token") override val token: String,
    @SerialName("version") override val version: Int,
    @SerialName("app_permissions") override val appPermissions: String? = null,
    @SerialName("message") val message: Message? = null,
    @SerialName("locale") val userLocale: String? = null,
    @SerialName("guild_locale") val guildLocale: String? = null
) : Interaction()

@Serializable
@SerialName("5")
public data class ModalSubmit(
    @SerialName("id") override val id: String,
    @SerialName("application_id") override val applicationId: String,
    @SerialName("data") val data: Data,
    @SerialName("guild_id") val guildId: String? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("member") val member: GuildMember? = null,
    @SerialName("user") val user: User? = null,
    @SerialName("token") override val token: String,
    @SerialName("version") override val version: Int,
    @SerialName("app_permissions") override val appPermissions: String? = null,
    @SerialName("locale") val userLocale: String? = null,
    @SerialName("guild_locale") val guildLocale: String? = null
) : Interaction() {
    @Serializable
    public data class Data(
        @SerialName("custom_id") public val modalCustomId: String,
        @SerialName("components") public val componentResponses: List<ActionRowResponse>
    )

    @Serializable
    public data class ActionRowResponse(public val components: List<ComponentResponse>)

    @Serializable
    public data class ComponentResponse(
        public val type: Int,
        @SerialName("custom_id") public val customId: String,
        public val value: String
    )
}

@Serializable
public data class CommandInteractionData(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: CommandType,
    @SerialName("resolved") val resolved: CommandInteractionDataResolved? = null,
    @SerialName("options") val options: List<CommandInteractionOptionResponse> = emptyList(),
    @SerialName("custom_id") val customId: String? = null,
    @SerialName("component_type") val componentType: Int? = null,
    @SerialName("values") val values: List<Unit>? = null,
    @SerialName("target_id") val targetId: String? = null,
)

@Serializable
public data class CommandInteractionDataResolved(
    @SerialName("users") val users: Map<String, User> = emptyMap(),
    @SerialName("members") val members: Map<String, PartialMember> = emptyMap(),
    @SerialName("roles") val roles: Map<String, Role> = emptyMap(),
    @SerialName("channels") val channels: Map<String, PartialChannel> = emptyMap(),
    // Note: API denotes "partial" but does not mention what constitutes a partial message
    @SerialName("messages") val messages: Map<String, Message> = emptyMap(),
    @SerialName("attachments") val attachments: Map<String, Attachment> = emptyMap()
)

@Serializable
public sealed class CommandInteractionOptionResponse {
    public abstract val name: String
}

@Serializable
@SerialName("1")
public data class SubCommandResponse(
    override val name: String,
    val options: List<CommandInteractionOptionResponse>,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("2")
public data class SubCommandGroupResponse(
    override val name: String,
    val options: List<CommandInteractionOptionResponse>,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("3")
public data class StringResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("4")
public data class IntegerResponse(
    override val name: String,
    val value: Int?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("5")
public data class BooleanResponse(
    override val name: String,
    val value: Boolean?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("6")
public data class UserResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("7")
public data class ChannelResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("8")
public data class RoleResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("9")
public data class MentionableResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("10")
public data class NumberResponse(
    override val name: String,
    val value: Float?,
) : CommandInteractionOptionResponse()

@Serializable
@SerialName("11")
public data class AttachmentResponse(
    override val name: String,
    val value: String?,
) : CommandInteractionOptionResponse()
