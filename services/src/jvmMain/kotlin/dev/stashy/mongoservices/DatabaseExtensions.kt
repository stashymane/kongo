package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.mongoservices.builders.RegistryBuilder
import dev.stashy.mongoservices.builders.codecRegistry

fun MongoDatabase.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(codecRegistry(fn))
