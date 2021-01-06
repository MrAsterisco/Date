# Date
Date is a Kotlin multiplatform library which provides implementation of date-related types in pure Kotlin.

Most notably, the `Date` class provides a way to deal with dates while maintaining platform abstraction.

## What's in the box
The library includes various classes to support multiple usages.

### Date
`Date` is an implementation of a normal date. it is backed by platform-specific implementations.

It mimics the behavior of a `java.util.Date` and it is backed by it on JVM platforms; on iOS, it wraps a `NSDate` with the necessary changes to make it behave as its counterpart on the JVM platform.

### Calendar
`Calendar` is an implementation of a calendar, that is the relationships between calendar units (such as days, months, and years) and absolute points in time (such as dates).

On JVM, it is backed by `java.util.Calendar`, whereas on iOS it is supported by `NSCalendar`.

### ISO8601DateFormat
`ISO8601DateFormat` is a pure Kotlin implementation of a formatter that follows the [ISO8601 standard](https://en.wikipedia.org/wiki/ISO_8601).

It is implemented using a regular expression and can parse strings into instances of `Date` as well as converting a `Date` to its IS08601 representation.

#### Limitations
At the moment, this class does not support parsing or converting dates in different timezones than UTC.

### DateSerializer
`DateSerializer` adds serialization support to `Date` instances via [KotlinX.Serialization](https://github.com/Kotlin/kotlinx.serialization).

Dates are serialized using `ISO8601DateFormat`.

### TimeZoneUtils
`TimeZoneUtils` is a utility class that abstracts using timezones in pure Kotlin.

On JVM, it is backend by `java.util.TimeZone`; on iOS, it is supported by `NSTimeZone`.

## Installation
Add the repository to your `build.gradle`:

```kotlin
repositories {
    maven("https://dl.bintray.com/mrasterisco/Maven")
}
```

Add the library as a dependency:

```kotlin
dependencies {
    implementation("io.github.mrasterisco:Date:<version>")
}
```

## Compatibility
The library provides platform-specific implementations for iOS and JVM (which is also compatible with Android).

|                      | iOS         | macOS | watchOS | tvOS | JVM         | nodeJS | browserJS | Windows | Linux |
|----------------------|-------------|-------|---------|------|-------------|--------|-----------|---------|-------|
| Supported            | YES         | NO    | NO      | NO   | YES         | NO     | NO        | NO      | NO    |
| Unit Tests           | YES, passed | N/A   | N/A     | N/A  | YES, passed | N/A    | N/A       | N/A     | N/A   |
| Published to Bintray | YES         | N/A   | N/A     | N/A  | YES         | N/A    | N/A       | N/A     | N/A   |

## Contributions
All contributions to expand the library are welcome. Fork the repo, make the changes you want, and open a Pull Request.

If you make changes to the codebase, I am not enforcing a coding style, but I may ask you to make changes based on how the rest of the library is made.

## Status
This library is under **active development** and it can be concisidered stable enough to be used in Production.

Even if most of the APIs are pretty straightforward, **they may change in the future**; but you don't have to worry about that, because releases will follow [Semantic Versioning 2.0.0](https://semver.org/).

## License
Date is distributed under the MIT license. [See LICENSE](https://github.com/MrAsterisco/Date/blob/main/LICENSE) for details.
