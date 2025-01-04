# MongoDB Kotlin driver services

A collection of utilities to improve the usability of the Kotlin MongoDB driver.

## Usage

Currently only available via my personal repo, will be published to Maven central on first stable release.

```kotlin
repositories {
    maven("https://repo.stashy.dev/releases")
}

dependencies {
    implementation("dev.stashy.mongoservices:mongodb-services:<version>")

    //model has no dependency on MongoDB libs and is included in the library above
    implementation("dev.stashy.mongoservices:model:<version>")
}
```

## Features

* Builder/DSL pattern for many operations (insert, update, index, etc.)
* Automatic SerialName fetching with operation builder pattern (via Kotlin reflection, not possible to do this otherwise
  currently)
* Service class for better ergonomics
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

### Service class

```kotlin
class DataService(db: MongoDatabase) : MongoService<Foo>("foo", db, Foo::class) {
    override suspend fun init() {
        super.init() // base function creates the collection
        collection.createIndex { Foo::bar.text() } // creates a text index for the `bar` property
    }

    fun search(query: String): Foo? = collection.find { text(query) }.singleOrNull()
}
```

## TODO

* Aggregate builder
* Projection builder
