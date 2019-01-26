package com.jessecorbett.diskord.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.matchesPredicate
import com.jessecorbett.diskord.api.model.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
    fun `should compute all base permissions for administrator`() {
        every { user.id } returns ownerId

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions.ALL)
    }

    @Test
    fun `should compute base permissions for administrative user`() {
        every { user.id } returns "user id"
        every { guild.permissions } returns Permission.ADMINISTRATOR.mask

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions.ALL)
    }

    @Test
    fun `should compute base permissions for user with no permissions`() {
        every { user.id } returns "user id"
        every { guild.permissions } returns 0

        assertThat(computeBasePermissions(member, guild)).isEqualTo(Permissions(0))
    }

    @Test
    fun `should compute base permissions for user with some permissions`() {
        val permissions = Permissions.of(Permission.VIEW_CHANNEL, Permission.SEND_MESSAGES)

        every { user.id } returns "user id"
        every { guild.permissions } returns permissions.value

        assertThat(computeBasePermissions(member, guild)).matchesPredicate { permissions in it }
    }
}
