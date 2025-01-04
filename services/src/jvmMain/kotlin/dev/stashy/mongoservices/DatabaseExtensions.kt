package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.mongoservices.builders.RegistryBuilder
import dev.stashy.mongoservices.builders.codecRegistry

fun MongoDatabase.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(codecRegistry(fn))

fun <T : Any> MongoCollection<T>.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(RegistryBuilder().apply(fn).build())

fun MongoClient.withCodecRegistry(fn: RegistryBuilder.() -> Unit) =
    withCodecRegistry(RegistryBuilder().apply(fn).build())
