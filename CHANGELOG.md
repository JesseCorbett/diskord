# Changelog

## [X.X.X] - YYYY-MM-DD

### Added
- Added class `Permissions` to help interact with permissions masks
- Added enum class `Permission` containing all currently supported permissions
- Added function `computePermissions` to determine permissions overwrites for a given channel (see Discord documentation for more information)

### Changed
- `Permissions` class is now used where permissions `Int` properties were previously used

## [1.4.0] - 2019-03-17

### Added
- User status support

### Changed
- Migrated from OkHttp to Ktor
- Reworked coroutine usage on websocket interface
- Enhanced logging tooling
- Updated dependencies

### Removed
- Pretty much all of the JVM specific code

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
