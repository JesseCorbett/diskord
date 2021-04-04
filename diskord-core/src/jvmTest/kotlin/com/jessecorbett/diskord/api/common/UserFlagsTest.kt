package com.jessecorbett.diskord.api.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlin.test.Test

internal class UserFlagsTest {
    @Test
    fun `should be equal to none if empty`() {
        val flags = UserFlags(0)

        assertThat(flags).isEqualTo(UserFlags.NONE)
    }

    @Test
    fun `should handle detecting a flag in flags (our values)`() {
        val flags = UserFlags(UserFlag.HYPESQUAD_EVENTS.mask + UserFlag.HOUSE_BRAVERY.mask)

        assertThat(UserFlag.HYPESQUAD_EVENTS in flags).isTrue()
        assertThat(UserFlag.HOUSE_BRAVERY in flags).isTrue()
        assertThat(UserFlag.HOUSE_BALANCE in flags).isFalse()
        assertThat(UserFlag.HOUSE_BRILLIANCE in flags).isFalse()
    }

    @Test
    fun `should handle detecting a flag in flags (real values)`() {
        val flags = UserFlags(640) // HOUSE_BRILLIANCE, EARLY_SUPPORTER

        assertThat(UserFlag.EARLY_SUPPORTER in flags).isTrue()
        assertThat(UserFlag.HOUSE_BRILLIANCE in flags).isTrue()
        assertThat(UserFlag.HOUSE_BRAVERY in flags).isFalse()
        assertThat(UserFlag.HOUSE_BALANCE in flags).isFalse()
    }
}
