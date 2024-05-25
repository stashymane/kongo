package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.MongoCollection
import dev.stashy.mongoservices.builders.*
import org.bson.conversions.Bson

interface MongoServiceBase<T : Any> {
    val collection: MongoCollection<T>
    suspend fun init()
}

inline fun <T : Any> MongoServiceBase<T>.sort(fn: SortBuilder<T>.() -> Bson): Bson = fn(SortBuilder())

inline fun <T : Any> MongoServiceBase<T>.index(fn: IndexBuilder<T>.() -> Bson): Bson = fn(IndexBuilder())

inline fun <T : Any> MongoServiceBase<T>.update(fn: UpdateBuilder<T>.() -> Bson): Bson = fn(UpdateBuilder())

inline fun <T : Any> MongoServiceBase<T>.filter(fn: FilterBuilder<T>.() -> Bson): Bson = fn(FilterBuilder())

inline fun <T : Any> MongoServiceBase<T>.projection(fn: ProjectionBuilder<T>.() -> Bson): Bson = fn(ProjectionBuilder())