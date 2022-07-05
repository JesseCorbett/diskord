package com.jessecorbett.diskord.api.invite

import com.jessecorbett.diskord.api.common.Invite
import com.jessecorbett.diskord.internal.client.RestClient
import com.jessecorbett.diskord.util.DiskordInternals
import io.ktor.client.call.*

/**
 * A REST client for a specific invite
 *
 * @param inviteCode The id of the invite
 * @param client The REST client implementation
 */
@OptIn(DiskordInternals::class)
public class InviteClient(public val inviteCode: String, client: RestClient) : RestClient by client {

    /**
     * Get this invite.
     *
     * @return This invite.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun getInvite(withCounts: Boolean = false): Invite {
        return GET("/invites/$inviteCode?with_counts=$withCounts").body()
    }

    /**
     * Get this invite.
     *
     * @return This invite.
     * @throws com.jessecorbett.diskord.api.exceptions.DiscordException
     */
    public suspend fun deleteInvite() {
        DELETE("/invites/$inviteCode").body<Unit>()
    }

}
