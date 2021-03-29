package com.jessecorbett.diskord.api.common

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlin.test.Test

internal class UserFlagsTest {
    @Test
    fun `should handle detecting a flag in flags`() {
        val flags = UserFlags.of(UserFlag.HYPESQUAD_EVENTS, UserFlag.HOUSE_BRAVERY)

        assertThat(UserFlag.HYPESQUAD_EVENTS in flags).isTrue()
        assertThat(UserFlag.HOUSE_BRAVERY in flags).isTrue()
        assertThat(UserFlag.HOUSE_BALANCE in flags).isFalse()
        assertThat(UserFlag.HOUSE_BRILLIANCE in flags).isFalse()
    }
}
