# Changelog

## [2.1.1] - 2020-09-35

### Fixed
* Two issues related to rate limiting

### Changed
* Added logging around rate limits and API exceptions

## [2.1.0] - 2021-09-09

### Breaking Changes
The following minor breaking changes have been made:
* `Guild.region` has been removed from the Discord API v9 (it has been replaced with `Channel.rtcRegion`)
* `Channel.rateLimitPerUser` has been marked as nullable to support threads (as per the Discord API)
* Fixed incorrectly named methods: `Guild.updateChannel` -> `Guild.updateGuild`, `Guild.deleteChannel` -> `Guild.deleteGuild`

### Added
* Added support for stickers
* Added support for threads
* Added extensions for sending embedded replies
* Added missing enums to `GuildFeatures`

### Changed
* JavaScript module now supports both Legacy and IR backends
* `FileData` now supports specifying a content type (this is required for sticker uploads)
* `Permission.MANAGE_EMOJIS` has been deprecated in favor of `Permission.MANAGE_EMOJIS_AND_STICKERS`
* `GatewayIntent.GUILD_EMOJIS` has been deprecated in favor of `Permission.GUILD_EMOJIS_AND_STICKERS`
* `MessageSticker` has been deprecated in favor of `Sticker`.
* `Message.stickers` has been deprecated in favor of `Message.stickerList`
* Simplified BotContext reply extensions so that embeds are optional parts of `reply` rather than their own distinct `replyEmbed`
* Updated kotlin to 1.5.30
* Updated kotlinx.serialization to 1.2.2
* Updated ktor dependency to 1.6.3

### Fixed
* Fixed an issue where emoji updates may not have been received
* Fixed incorrect permissions mask for VIEW_GUILD_INSIGHTS
* `Permissions` now uses `Long` for internal bitmask representation

## [2.0.2] - 2021-07-03

### Added
Experimental support for JavaScript IR target

### Changed
Upgrade to kotlin 1.5.20

## [2.0.1] - 2021-07-03

### Fixed
- Fixed an issue where rate limit errors might overly aggressively retry

## [2.0.0] - 2021-06-18

### !! This is a major breaking change !!
It is recommended to read through the documentation as this is essentially
a complete rewrite with a new API, new artifacts, and new usage

### Added
- Added multiple API endpoints which were missing
- New Bot DSL framework

### Changed
- Separated into two artifacts
    - `diskord-core` which contains the core REST API and Gateway implementation
    - `diskord-bot` which contains the high level DSL and utilities
- New DSL
- `diskord-bot` ships with a simple logger implementation by default
- New interface with the gateway
- REST clients are now backed by a shared `RestClient` which manages
rate limits across requests more intelligently
- Updated audit logging to automatically translate JSON into concrete classes
- The Command DSL is now the Classic Command DSL, as we develop a new Command DSL using the slash command API
- The Classic Command DSL now passes messages as `it` instead of `this`

### Removed
- Old experimental DSL
- `DiscordGateway`

## [1.8.0] - 2020-10-18

### Added
- Added support for gateway intents.

## [1.7.3] - 2020-08-09

### Added/Fixed
* Fixed an issue with `GuildClient.createBan` not sending the correct request
* Added `CreateGuildBan` to hold `GuildClient.createBan` JSON payload

## [1.7.2] - 2020-08-09

### Fixed
- Fixed the CDN domain after Discord decided not to migrate the CDN

## [1.7.1] - 2020-07-31

### Fixed
- Fixed an issue where the user agent reported the wrong version

## [1.7.0] - 2020-07-29

### Added/Fixed
Addressed an issue where the `GuildClient.updateMember` method did not allow disconnecting
a guild member from voice channels.

The solution is the addition of `GuildClient.disconnectMemberVoiceChannel` which under the hood performs the same API
request but only disconnects members from voice.

### Changed
- Updated kotlinx.coroutines to 1.3.8

## [1.6.2] - 2020-04-11

### Fixed
Fixed an issue where unusually large API response bodies broke deserialization

## [1.6.1] - 2020-04-05

### Fixed
Addressed an issue where ktor does not close the receiving channel of the websocket client when the connection closes

## [1.6.0] - 2020-04-04

### Breaking Change
Ktor has been updated to the 1.3.x release.  This update merges an external 
dependency, which resulted in a package name change.  If you are using the FileData 
object with a raw byte packet, then make sure to update the following packages: 

    import kotlinx.io. -> import io.ktor.utils.io.
    import kotlinx.coroutines.io. -> import io.ktor.utils.io.

### Added
- Added missing property in GuildCreated event payload
- Added default value for nick property in GuildMemberUpdate event payload

### Changed
- Upgraded to Kotlin 1.3.71

## [1.5.3] - 2020-01-14

### Changed
- Added @DiskordInternal annotation to internal APIs which should be avoided
- Switched to a stable JSON configuration from kotlinx.serialization
- Updated dependencies
- Removed transitive dependency on jcenter

### Fixed
- Updated audit events to match changes to the API
- Soft locking on connection issues
- An issue where updating GuildMembers would fail on serialization

## [1.5.2] - 2019-11-12

### Added
- Added emoji field to `UserStatusActivity` to support custom statuses

### Changed
- Added exponential backoff to websocket connections to avoid session limits when there are connection issues with the API

### Fixed
- Deserialization of Unicode emoji failing due to null id
- Discord API issues causing Diskord to soft crash

## [1.5.1] - 2019-10-07

### Added
- Very simple bot example
- Very simple command DSL example

### Changed
- Simplified gradle setup, due to gradle features graduating
- Updated dependencies
- Updated Dokka

### Fixed
- Fixed webhook patching responses not parsing json correctly

## [1.5.0] - 2019-09-28

### Added
- Added api to upload files

### Changed
- Updated dependencies
- Using kotlin 1.3.50

## [1.4.2] - 2019-08-20

*Note: Several classes and constants have been re-arranged in the* `com.jessecorbett.diskord.api.rest.client.internal` 
*package to help facilitate better testing. Any code depending on these changes will break. It is highly recommended 
not to depend on any code marked with the* `@DiskordInternals` *annotation in the future, as we provide no guarantee 
that this code will not be changed between releases, even between minor or patch releases.*

### Added
- Added @DiskordInternals annotation to mark internal APIs.

### Changed
- REST clients have been refactored to better support testing.  This may break client code depending on Diskord internal code.
- Updated Kotlin version to 1.3.41
- Updated ActivityType enum

## [1.4.1] - 2019-05-30

### Changed
- Updated ktor version
- Updated kotlin version to 1.3.40
- Removed deprecated kotlinx.serialization @Optional annotation
- Update Gradle build with Kotlin DSL & updates to publish to Maven instead of Bintray (to fix publishing issues)

## [1.4.0] - 2019-05-22

### Changed
- Changed artifact path for jvm build from `diskord` to `diskord-jvm`, introducing `diskord` as the multiplatform artifact
- Migrated backend to ktor multiplatform, allowing diskord to be built out for other platforms

## [1.3.4] - 2019-05-18

### Changed
- Fixed a bug where `ChannelClient.getMessageReactions` function invoked with a String containing a unicode emoji was indefinitely hanging
- Fixed a bug where both `ChannelClient.getMessageReactions` functions were returning the incorrect type

## [1.3.3] - 2019-05-05

### Changed
- Fixed a bug where Emoji had a list of Role models instead of ids

## [1.3.0] - 2019-02-03

### Changed
- Ported internal structure to be a multiplatform project
- Ported to kotlinx.serialization from jackson

## [1.2.0] - 2019-01-20

### Added
- Added a typealias `Color` for `Int` to be used in embeds and roles.
- Added object `Colors`, providing a list of predefined `Color` values, as well as conversion functions for RGB and hex values.

### Changed
- SNAPSHOT builds now maintain same naming scheme as release builds, remain in gitlab packages repository.
- `Message.delete` and `MessageUpdate.delete` extension functions will now filter out `DiscordNotFoundExceptions` as they are not meaningful exceptions in that scenario.

### Other notes
- Gradle CI/CD build has been optimized.
- Initial project and architectural changes have begun for migrating to a multiplatform project.
