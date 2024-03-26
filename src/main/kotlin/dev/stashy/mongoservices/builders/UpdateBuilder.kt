package dev.stashy.mongoservices.builders

import com.mongodb.client.model.PushOptions
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class UpdateBuilder<T> {
    fun <V> set(value: Pair<KProperty1<T, V>, V>): Bson = Updates.set(value.first.serialName(), value.second)
    fun <V> T.setFields(fields: List<KProperty1<T, V>>): Bson =
        combine(fields.map { field -> set(fieldToValue(field)) })

    fun <V> unset(field: KProperty1<T, V>): Bson = Updates.unset(field.serialName())
    fun setOnInsert(value: Bson): Bson = Updates.setOnInsert(value)
    fun <V> rename(value: Pair<KProperty1<T, V>, String>): Bson = Updates.rename(value.first.serialName(), value.second)

    fun <V : Number> increment(field: KProperty1<T, V>, amount: V): Bson = Updates.inc(field.serialName(), amount)
    fun <V : Number> multiply(field: KProperty1<T, V>, amount: V): Bson = Updates.mul(field.serialName(), amount)

    fun <V> min(field: KProperty1<T, V>, value: V & Any): Bson = Updates.min(field.serialName(), value)
    fun <V> max(field: KProperty1<T, V>, value: V & Any): Bson = Updates.max(field.serialName(), value)

    fun <V> currentDate(field: KProperty1<T, V>): Bson = Updates.currentDate(field.serialName())
    fun <V> currentTimestamp(field: KProperty1<T, V>): Bson = Updates.currentTimestamp(field.serialName())

    fun <V> addToSet(field: KProperty1<T, V>, item: V): Bson = Updates.addToSet(field.serialName(), item)
    fun <V> addEachToSet(field: KProperty1<T, V>, items: List<V>): Bson =
        Updates.addEachToSet(field.serialName(), items)

    fun <V> push(field: KProperty1<T, V>, item: V): Bson = Updates.push(field.serialName(), item)
    fun <V> pushEach(field: KProperty1<T, V>, items: List<V>, options: PushOptions = PushOptions()): Bson =
        Updates.pushEach(field.serialName(), items, options)

    fun <V> pull(field: KProperty1<T, V>, value: V): Bson = Updates.pull(field.serialName(), value)
    fun pullByFilter(filter: Bson): Bson = Updates.pullByFilter(filter)
    fun <V> pullAll(field: KProperty1<T, V>, values: List<V>): Bson = Updates.pullAll(field.serialName(), values)

    fun <V> popFirst(field: KProperty1<T, V>): Bson = Updates.popFirst(field.serialName())
    fun <V> popLast(field: KProperty1<T, V>): Bson = Updates.popLast(field.serialName())

    fun <V> bitwiseAnd(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseAnd(field.serialName(), value)
    fun <V> bitwiseAnd(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseAnd(field.serialName(), value)
    fun <V> bitwiseOr(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseOr(field.serialName(), value)
    fun <V> bitwiseOr(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseOr(field.serialName(), value)
    fun <V> bitwiseXor(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseXor(field.serialName(), value)
    fun <V> bitwiseXor(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseXor(field.serialName(), value)

    infix fun Bson.and(value: Bson): Bson = Updates.combine(this, value)

    fun combine(vararg updates: Bson): Bson = Updates.combine(*updates)
    fun combine(updates: List<Bson>): Bson = Updates.combine(updates)
}
