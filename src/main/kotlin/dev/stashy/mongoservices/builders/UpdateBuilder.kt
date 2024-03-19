package dev.stashy.mongoservices.builders

import com.mongodb.client.model.PushOptions
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class UpdateBuilder<T> {
    fun <V> set(value: Pair<KProperty1<T, V>, V>): Bson = Updates.set(value.first.name, value.second)
    fun <V> unset(field: KProperty1<T, V>): Bson = Updates.unset(field.name)
    fun setOnInsert(value: Bson): Bson = Updates.setOnInsert(value)
    fun <V> rename(value: Pair<KProperty1<T, V>, String>): Bson = Updates.rename(value.first.name, value.second)

    fun <V : Number> increment(field: KProperty1<T, V>, amount: V): Bson = Updates.inc(field.name, amount)
    fun <V : Number> multiply(field: KProperty1<T, V>, amount: V): Bson = Updates.mul(field.name, amount)

    fun <V> min(field: KProperty1<T, V>, value: V & Any): Bson = Updates.min(field.name, value)
    fun <V> max(field: KProperty1<T, V>, value: V & Any): Bson = Updates.max(field.name, value)

    fun <V> currentDate(field: KProperty1<T, V>): Bson = Updates.currentDate(field.name)
    fun <V> currentTimestamp(field: KProperty1<T, V>): Bson = Updates.currentTimestamp(field.name)

    fun <V> addToSet(field: KProperty1<T, V>, item: V): Bson = Updates.addToSet(field.name, item)
    fun <V> addEachToSet(field: KProperty1<T, V>, items: List<V>): Bson = Updates.addEachToSet(field.name, items)

    fun <V> push(field: KProperty1<T, V>, item: V): Bson = Updates.push(field.name, item)
    fun <V> pushEach(field: KProperty1<T, V>, items: List<V>, options: PushOptions = PushOptions()): Bson =
        Updates.pushEach(field.name, items, options)

    fun <V> pull(field: KProperty1<T, V>, value: V): Bson = Updates.pull(field.name, value)
    fun pullByFilter(filter: Bson): Bson = Updates.pullByFilter(filter)
    fun <V> pullAll(field: KProperty1<T, V>, values: List<V>): Bson = Updates.pullAll(field.name, values)

    fun <V> popFirst(field: KProperty1<T, V>): Bson = Updates.popFirst(field.name)
    fun <V> popLast(field: KProperty1<T, V>): Bson = Updates.popLast(field.name)

    fun <V> bitwiseAnd(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseAnd(field.name, value)
    fun <V> bitwiseAnd(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseAnd(field.name, value)
    fun <V> bitwiseOr(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseOr(field.name, value)
    fun <V> bitwiseOr(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseOr(field.name, value)
    fun <V> bitwiseXor(field: KProperty1<T, V>, value: Int): Bson = Updates.bitwiseXor(field.name, value)
    fun <V> bitwiseXor(field: KProperty1<T, V>, value: Long): Bson = Updates.bitwiseXor(field.name, value)

    infix fun Bson.and(value: Bson): Bson = Updates.combine(this, value)

    fun combine(vararg updates: Bson): Bson = Updates.combine(*updates)
    fun combine(updates: List<Bson>): Bson = Updates.combine(updates)
}
