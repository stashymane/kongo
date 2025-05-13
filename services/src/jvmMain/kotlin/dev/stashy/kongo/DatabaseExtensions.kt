package dev.stashy.kongo

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.kongo.builders.RegistryBuilder
import dev.stashy.kongo.builders.codecRegistry

fun MongoDatabase.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(codecRegistry(fn))

fun <T : Any> MongoCollection<T>.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(RegistryBuilder().apply(fn).build())

fun MongoClient.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(RegistryBuilder().apply(fn).build())
