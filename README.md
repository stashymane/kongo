# kongo

![Maven Central][Maven Central badge]
[![Documentation][Docs badge]][Docs URL]

A collection of utilities to improve the usability of the Kotlin MongoDB driver.

---

## Usage

Currently only available via my personal repo, will be published to Maven central on first stable release.

```kotlin
repositories {
    maven("https://repo.stashy.dev/releases")
}

dependencies {
    implementation("dev.stashy.kongo:services:<version>")

    //model has no dependency on MongoDB libs and is included in the library above
    implementation("dev.stashy.kongo:model:<version>")
}
```

## Features

* Builder/DSL pattern for many operations (insert, update, index, etc.)
* Automatic SerialName fetching with operation builder pattern (via Kotlin reflection, not possible to do this otherwise
  currently)
* Service interface for better ergonomics
* ObjectId replacement (`DocumentId`) for sharing your data model across projects without including the MongoDB driver
  itself.

## Examples

### Filter builder

```kotlin
data class Foo(@SerialName("_id") @Contextual val id: DocumentId, val bar: String)

filter {
    (Foo::bar equals "bar") or (Foo::bar equals "baz")
}

// the above is equivalent to:

Filters.or(
    Filters.eq(Foo::bar.name, "bar"),
    Filters.eq(Foo::bar.name, "baz")
)

// ...with the added benefit of automatically
// using names from the SerialName annotation.
```

[Maven Central badge]: https://img.shields.io/badge/maven--central-soon-lightgray

[Docs badge]: https://img.shields.io/badge/docs-dokka-blue

[Docs URL]: https://stashymane.github.io/kongo/
