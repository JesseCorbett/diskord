package com.jessecorbett.diskord.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.matchesPredicate
import com.jessecorbett.diskord.api.model.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// FIXME: Move to commonTest when class-under-test is moved to commonMain.
//  This means that @MockK will need to be replaced with explicit mocks in init().

@ExtendWith(MockKExtension::class)
class PermissionsTest {
    private val ownerId = "owner id"

    @MockK
    lateinit var user: User

    @MockK
    lateinit var member: GuildMember

    @MockK
    lateinit var guild: Guild

    @MockK
    lateinit var channel: Channel

    @BeforeEach
    fun init() {
        every { guild.ownerId } returns ownerId
        every { member.user } returns user
    }

    @Test
    fun `should compute all base permissions for server owner`() {
        every { user.id } returns ownerId

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions.ALL)
    }

    @Test
    fun `should compute all base permissions for administrative user`() {
        every { user.id } returns "user id"
        every { guild.permissions } returns Permission.ADMINISTRATOR.mask

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions.ALL)
    }

    @Test
    fun `should compute base permissions for user with no permissions`() {
        every { user.id } returns "user id"
        every { guild.permissions } returns 0

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions.NONE)
    }

    @Test
    fun `should compute base permissions for user with some permissions`() {
        val permissions = Permissions.of(Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES)

        every { user.id } returns "user id"
        every { guild.permissions } returns permissions.value

        assertThat(computeBasePermissions(member, guild)).matchesPredicate { permissions in it }
    }

    @Test
    fun `should compute all overwrites for administrative user`() {
        val permissions = Permissions.of(Permission.ADMINISTRATOR)

        assertThat(computeOverwrites(permissions, member, channel, guild)).isEqualTo(Permissions.ALL)
    }

    @Test
    fun `should compute all overwrites for user with no permissions and no overwrites`() {
        val basePermissions = Permissions.NONE
        val everyone = mockRole("@everyone")

        every { guild.roles } returns listOf(everyone)
        every { channel.permissionOverwrites } returns listOf()

        assertThat(computeOverwrites(basePermissions, member, channel, guild)).isEqualTo(Permissions.NONE)
    }

    @Test
    fun `should compute all overwrites for user with basic permissions and @everyone overwrites`() {
        val basePermissions = Permissions.of(Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES)

        val everyone = mockRole("@everyone")
        val everyoneOverwrites = mockOverwrite(OverwriteType.ROLE, "@everyone",
            denied = Permissions.of(Permission.SEND_MESSAGES),
            allowed = Permissions.of(Permission.READ_MESSAGE_HISTORY))

        every { member.roleIds } returns listOf()
        every { guild.roles } returns listOf(everyone)
        every { channel.permissionOverwrites } returns listOf(everyoneOverwrites)

        assertThat(computeOverwrites(basePermissions, member, channel, guild))
            .isEqualTo(Permissions.of(Permission.VIEW_CHANNEL, Permission.READ_MESSAGE_HISTORY))
    }

    @Test
    fun `should compute all overwrites for user with multiple roles and simple overwrites`() {
        val basePermissions = Permissions.of(Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES,
            Permission.KICK_MEMBERS, Permission.BAN_MEMBERS)

        val everyone = mockRole("@everyone", Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES)
        val voice = mockRole("voice")
        val moderator = mockRole("moderator", Permission.KICK_MEMBERS, Permission.BAN_MEMBERS)

        val everyoneOverwrites = mockOverwrite(OverwriteType.ROLE, "@everyone")
        val voiceOverwrites = mockOverwrite(OverwriteType.ROLE, "voice",
            allowed = Permissions.of(Permission.SPEAK, Permission.CONNECT))
        val moderatorOverwrites = mockOverwrite(OverwriteType.ROLE, "moderator")

        every { member.roleIds } returns listOf("voice", "moderator")
        every { guild.roles } returns listOf(everyone, voice, moderator)
        every { channel.permissionOverwrites } returns listOf(everyoneOverwrites, voiceOverwrites, moderatorOverwrites)

        assertThat(computeOverwrites(basePermissions, member, channel, guild))
            .isEqualTo(Permissions.of(Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES,
                Permission.KICK_MEMBERS, Permission.BAN_MEMBERS, Permission.SPEAK, Permission.CONNECT))
    }


    fun `should compute all overwrites for user with multiple roles and complex overwrites`() {}

    private fun mockRole(id: String, vararg permissions: Permission)
            = mockRole(id, permissions = if (permissions.isEmpty()) { Permissions.NONE } else { Permissions.of(*permissions) })

    private fun mockRole(id: String, name: String = id, permissions: Permissions) = mockk<Role>().also {
        every { it.id } returns id
        every { it.name } returns name
        every { it.permissions } returns permissions.value
    }

    private fun mockOverwrite(
        type: OverwriteType,
        id: String,
        denied: Permissions = Permissions.NONE,
        allowed: Permissions = Permissions.NONE
    ) = mockk<Overwrite>().also {
        every { it.type } returns type
        every { it.id } returns id
        every { it.denied } returns denied.value
        every { it.allowed } returns allowed.value
    }
}
