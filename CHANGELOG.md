# Changelog

## [1.4.1] - 2019-05-30

### Changed
- Updated ktor version
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
