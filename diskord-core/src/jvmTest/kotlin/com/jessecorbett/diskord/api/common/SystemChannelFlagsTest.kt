package com.jessecorbett.diskord.api.common

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

internal class SystemChannelFlagsTest {
    @Test
    fun `should handle flags which contain certain flags`() {
        val flags = SystemChannelFlags.of(SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS)

        assertThat(SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS in flags).isTrue()
        assertThat(SystemChannelFlag.SUPPRESS_PREMIUM_SUBSCRIPTIONS in flags).isFalse()
    }

    @Test
    fun `should handle adding flags`() {
        var flags = SystemChannelFlags.NONE + SystemChannelFlags.of(SystemChannelFlag.SUPPRESS_PREMIUM_SUBSCRIPTIONS)
        assertThat(SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS in flags).isFalse()
        assertThat(SystemChannelFlag.SUPPRESS_PREMIUM_SUBSCRIPTIONS in flags).isTrue()

        flags += SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS
        assertThat(SystemChannelFlag.SUPPRESS_JOIN_NOTIFICATIONS in flags).isTrue()
    }

    @Test
    fun `should handle removing flags`() {
        val flags = SystemChannelFlags.ALL - SystemChannelFlag.SUPPRESS_PREMIUM_SUBSCRIPTIONS
        assertThat(SystemChannelFlag.SUPPRESS_PREMIUM_SUBSCRIPTIONS in flags).isFalse()
    }
}
