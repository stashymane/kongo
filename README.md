# kongo

[![Maven Central][Maven Central badge]][Maven Central project]
[![Documentation][Docs badge]][Docs URL]

Transform the MongoDB Kotlin driver into actual Kotlin.

Still experimental, expect breaking changes until `1.0`.

## Usage

`build.gradle.kts`

```kotlin
dependencies {
    // MongoDB utilities, multiplatform but JVM-only
    // if using in a non-multiplatform project, add the `-jvm` suffix (services-jvm)
    implementation("dev.stashy.kongo:services:<version>")

    // DocumentID only - does not depend on MongoDB libs, multiplatform
    implementation("dev.stashy.kongo:model:<version>")
}
```

`libs.versions.toml`

```toml
[libraries]
kongo-model = { module = "dev.stashy.kongo:model", version.ref = "kongo" }
kongo-services = { module = "dev.stashy.kongo:services", version.ref = "kongo" }
```

You can only include either `model` or `services` in a module.
Including both will cause a conflict.

## Features

* Builder/DSL pattern for operations (insert, update, index, etc.)
* Serialization support
* Automatic `@SerialName` use in BSON
* Service interface for better ergonomics and type safety
* `DocumentId` - an ObjectId replacement that you can use in non-server modules, without the need to import **any**
  MongoDB libraries.

Most importantly: you can use as much of this library as you need.
Everything is a wrapper over the actual MongoDB Kotlin driver, allowing you to adopt the library in stages if needed.

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

### Collection extensions

```kotlin
val collection: MongoCollection<Foo> = TODO()

collection.find { Foo::id equals "bar" }.first()

collection.updateOne(
    options = { upsert(true) },
    filter = { Foo::id equals "bar" },
    update = { Foo::bar set "baz" }
)
```

### Service class

```kotlin
class FooService : KongoService<Foo> {
    override val info by meta(name = "test")
    override val database by inject() // provide the instance however you want

    suspend fun getFoo(bar: String): Result<Foo> = operation {
        find { Foo::bar equals bar }.first()
    }
}
```

See [KongoService documentation][Service documentation] for more information.

[Maven Central badge]: https://img.shields.io/maven-central/v/dev.stashy.kongo/services

[Maven Central project]: https://central.sonatype.com/artifact/dev.stashy.kongo/services

[Docs badge]: https://img.shields.io/badge/docs-dokka-blue

[Docs URL]: https://stashymane.github.io/kongo/

[Service documentation]: https://stashymane.github.io/kongo/services/dev.stashy.kongo.service/-kongo-service/index.html
