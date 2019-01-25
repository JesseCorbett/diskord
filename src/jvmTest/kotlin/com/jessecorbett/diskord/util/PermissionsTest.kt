package com.jessecorbett.diskord.util

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.jessecorbett.diskord.api.model.Permission
import com.jessecorbett.diskord.api.model.Permissions
import kotlin.test.Test

class PermissionsTest {
    @Test
    fun `administrator should have all permissions`() {
        val permissions = Permissions.of(Permission.ADMINISTRATOR)

        assertThat(Permission.ADMINISTRATOR in permissions).isTrue()
        assertThat(Permission.VIEW_AUDIT_LOG in permissions).isTrue()
        assertThat(Permission.MANAGE_CHANNELS in permissions).isTrue()
        assertThat(Permission.READ_MESSAGE_HISTORY in permissions).isTrue()
    }

    @Test
    fun `should handle roles which have certain permissions`() {
        val permissions = Permissions.of(Permission.READ_MESSAGE_HISTORY, Permission.KICK_MEMBERS)

        assertThat(Permission.READ_MESSAGE_HISTORY in permissions).isTrue()
        assertThat(Permission.KICK_MEMBERS in permissions).isTrue()
        assertThat(Permission.BAN_MEMBERS in permissions).isFalse()
    }
}
