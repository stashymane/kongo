# kongo

[![Maven Central][Maven Central badge]][Maven Central project]
[![Documentation][Docs badge]][Docs URL]

A collection of utilities to improve the usability of the Kotlin MongoDB driver.

## Usage

```kotlin
dependencies {
    // depends on MongoDB libs
    // if using in a JVM-only project, use `services-jvm`
    implementation("dev.stashy.kongo:services:<version>")

    // does not depend on MongoDB libs, is multiplatform
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
data class Foo(@SerialName("_id") val id: DocumentId, val bar: String)

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

### Service class

See [KongoService documentation][Service documentation].

[Maven Central badge]: https://img.shields.io/maven-central/v/dev.stashy.kongo/services

[Maven Central project]: https://central.sonatype.com/artifact/dev.stashy.kongo/services

[Docs badge]: https://img.shields.io/badge/docs-dokka-blue

[Docs URL]: https://stashymane.github.io/kongo/

[Service documentation]: https://stashymane.github.io/kongo/services/dev.stashy.kongo.service/-kongo-service/index.html
