package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class FilterBuilder<T> {
    infix fun <V> KProperty1<T, V>.equals(value: V): Bson = Filters.eq(name, value)
    infix fun <V> KProperty1<T, V>.notEquals(value: V): Bson = Filters.ne(name, value)

    infix fun <V> KProperty1<T, V>.greaterThan(value: V & Any): Bson = Filters.gt(name, value)
    infix fun <V> KProperty1<T, V>.lessThan(value: V & Any): Bson = Filters.lt(name, value)

    infix fun <V> KProperty1<T, V>.greaterOrEquals(value: V & Any): Bson = Filters.gte(name, value)
    infix fun <V> KProperty1<T, V>.lessOrEquals(value: V & Any): Bson = Filters.lte(name, value)

    infix fun <V> KProperty1<T, V>.containedIn(value: Iterable<V>): Bson = Filters.`in`(name, value)
    infix fun <V> KProperty1<T, V>.notContainedIn(value: Iterable<V>): Bson = Filters.nin(name, value)

    infix fun <V> KProperty1<T, V>.all(value: Iterable<V>): Bson = Filters.all(name, value)

    infix fun Bson.and(value: Bson): Bson = Filters.and(this, value)
    infix fun Bson.or(value: Bson): Bson = Filters.or(this, value)
    infix fun Bson.nor(value: Bson): Bson = Filters.nor(this, value)
}