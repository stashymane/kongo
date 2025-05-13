package dev.stashy.kongo

import com.mongodb.kotlin.client.coroutine.MongoCollection
import dev.stashy.kongo.builders.*
import org.bson.conversions.Bson

interface MongoServiceBase<T : Any> {
    val collection: MongoCollection<T>
    suspend fun init()
}


/**
 * Builder for sorts.
 * @see com.mongodb.client.model.Sorts
 */
inline fun <T : Any> MongoServiceBase<T>.sort(fn: SortBuilder<T>.() -> Bson): Bson = fn(SortBuilder())

/**
 * Builder for indexes.
 * @see com.mongodb.client.model.Indexes
 */
inline fun <T : Any> MongoServiceBase<T>.index(fn: IndexBuilder<T>.() -> Bson): Bson = fn(IndexBuilder())

/**
 * Builder for updates.
 * @see com.mongodb.client.model.Updates
 */
inline fun <T : Any> MongoServiceBase<T>.update(fn: UpdateBuilder<T>.() -> Bson): Bson = fn(UpdateBuilder())

/**
 * Builder for filters.
 * @see com.mongodb.client.model.Filters
 */
inline fun <T : Any> MongoServiceBase<T>.filter(fn: FilterBuilder<T>.() -> Bson): Bson = fn(FilterBuilder())

/**
 * Builder for projections.
 * @see com.mongodb.client.model.Projections
 */
inline fun <T : Any> MongoServiceBase<T>.projection(fn: ProjectionBuilder<T>.() -> Bson): Bson = fn(ProjectionBuilder())
