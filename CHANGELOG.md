# Changelog

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
