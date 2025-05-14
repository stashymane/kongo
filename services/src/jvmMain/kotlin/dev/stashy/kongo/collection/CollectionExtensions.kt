package dev.stashy.kongo.collection

import com.mongodb.client.model.*
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import dev.stashy.kongo.builders.FilterBuilder
import dev.stashy.kongo.builders.IndexBuilder
import dev.stashy.kongo.builders.UpdateBuilder
import org.bson.conversions.Bson

inline fun <reified T : Any> MongoCollection<T>.find(
    filter: FilterBuilder<T>.() -> Bson
): FindFlow<T> = find(filter(FilterBuilder()))

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndUpdate(
    options: FindOneAndUpdateOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): T? = findOneAndUpdate(filter(FilterBuilder()), update(UpdateBuilder()), FindOneAndUpdateOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndDelete(
    options: FindOneAndDeleteOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson
): T? = findOneAndDelete(filter(FilterBuilder()), FindOneAndDeleteOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndReplace(
    replacement: T,
    options: FindOneAndReplaceOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson
): T? = findOneAndReplace(filter(FilterBuilder()), replacement, FindOneAndReplaceOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.updateOne(
    options: UpdateOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): UpdateResult = updateOne(filter(FilterBuilder()), update(UpdateBuilder()), UpdateOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.deleteOne(
    options: DeleteOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson
): DeleteResult = deleteOne(filter(FilterBuilder()), DeleteOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.replaceOne(
    replacement: T,
    options: ReplaceOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson
): UpdateResult = replaceOne(filter(FilterBuilder()), replacement, ReplaceOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.updateMany(
    options: UpdateOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): UpdateResult = updateMany(filter(FilterBuilder()), update(UpdateBuilder()), UpdateOptions().apply(options))

suspend inline fun <reified T : Any> MongoCollection<T>.deleteMany(
    options: DeleteOptions.() -> Unit = { },
    filter: FilterBuilder<T>.() -> Bson
): DeleteResult = deleteMany(filter(FilterBuilder()), DeleteOptions().apply(options))

suspend inline fun <T : Any> MongoCollection<T>.createIndex(
    options: IndexOptions.() -> Unit = { },
    fn: IndexBuilder<T>.() -> Bson
): String = createIndex(fn(IndexBuilder()), IndexOptions().apply(options))
