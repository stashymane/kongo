package dev.stashy.mongoservices

import com.mongodb.client.model.*
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import dev.stashy.mongoservices.builders.FilterBuilder
import dev.stashy.mongoservices.builders.IndexBuilder
import dev.stashy.mongoservices.builders.UpdateBuilder
import org.bson.conversions.Bson

inline fun <reified T : Any> MongoCollection<T>.find(
    filter: FilterBuilder<T>.() -> Bson
): FindFlow<T> = find(filter(FilterBuilder()))

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndUpdate(
    options: FindOneAndUpdateOptions = FindOneAndUpdateOptions(),
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): T? = findOneAndUpdate(filter(FilterBuilder()), update(UpdateBuilder()), options)

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndDelete(
    options: FindOneAndDeleteOptions = FindOneAndDeleteOptions(),
    filter: FilterBuilder<T>.() -> Bson
): T? = findOneAndDelete(filter(FilterBuilder()), options)

suspend inline fun <reified T : Any> MongoCollection<T>.findOneAndReplace(
    replacement: T,
    options: FindOneAndReplaceOptions = FindOneAndReplaceOptions(),
    filter: FilterBuilder<T>.() -> Bson
): T? = findOneAndReplace(filter(FilterBuilder()), replacement, options)

suspend inline fun <reified T : Any> MongoCollection<T>.updateOne(
    options: UpdateOptions = UpdateOptions(),
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): UpdateResult = updateOne(filter(FilterBuilder()), update(UpdateBuilder()), options)

suspend inline fun <reified T : Any> MongoCollection<T>.deleteOne(
    options: DeleteOptions = DeleteOptions(),
    filter: FilterBuilder<T>.() -> Bson
): DeleteResult = deleteOne(filter(FilterBuilder()), options)

suspend inline fun <reified T : Any> MongoCollection<T>.replaceOne(
    replacement: T,
    options: ReplaceOptions = ReplaceOptions(),
    filter: FilterBuilder<T>.() -> Bson
): UpdateResult = replaceOne(filter(FilterBuilder()), replacement, options)

suspend inline fun <reified T : Any> MongoCollection<T>.updateMany(
    options: UpdateOptions = UpdateOptions(),
    filter: FilterBuilder<T>.() -> Bson,
    update: UpdateBuilder<T>.() -> Bson
): UpdateResult = updateMany(filter(FilterBuilder()), update(UpdateBuilder()), options)

suspend inline fun <reified T : Any> MongoCollection<T>.deleteMany(
    options: DeleteOptions = DeleteOptions(),
    filter: FilterBuilder<T>.() -> Bson
): DeleteResult = deleteMany(filter(FilterBuilder()), options)

suspend inline fun <T : Any> MongoCollection<T>.createIndex(
    options: IndexOptions = IndexOptions(), fn: IndexBuilder<T>.() -> Bson
): String = createIndex(fn(IndexBuilder()), options)
