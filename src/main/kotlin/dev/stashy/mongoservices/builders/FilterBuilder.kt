package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Filters
import com.mongodb.client.model.TextSearchOptions
import com.mongodb.client.model.geojson.Geometry
import com.mongodb.client.model.geojson.Point
import org.bson.BsonType
import org.bson.conversions.Bson
import java.util.regex.Pattern
import kotlin.reflect.KProperty1

class FilterBuilder<T> {
    infix fun <V> KProperty1<T, V>.equals(value: V): Bson = Filters.eq(name, value)
    infix fun <V> KProperty1<T, V>.notEquals(value: V): Bson = Filters.ne(name, value)

    infix fun <V> KProperty1<T, V>.greaterThan(value: V & Any): Bson = Filters.gt(name, value)
    infix fun <V> KProperty1<T, V>.lessThan(value: V & Any): Bson = Filters.lt(name, value)

    infix fun <V> KProperty1<T, V>.greaterOrEquals(value: V & Any): Bson = Filters.gte(name, value)
    infix fun <V> KProperty1<T, V>.lessOrEquals(value: V & Any): Bson = Filters.lte(name, value)

    infix fun <V> KProperty1<T, V>.containedIn(value: Iterable<V>): Bson = Filters.`in`(name, value)
    fun <V> KProperty1<T, V>.containedIn(vararg value: V): Bson = Filters.`in`(name, *value)

    infix fun <V> KProperty1<T, V>.notContainedIn(value: Iterable<V>): Bson = Filters.nin(name, value)
    fun <V> KProperty1<T, V>.notContainedIn(vararg value: V): Bson = Filters.nin(name, *value)

    infix fun Bson.and(value: Bson): Bson = Filters.and(this, value)
    fun Bson.and(vararg value: Bson): Bson = Filters.and(this, *value)
    infix fun Bson.and(value: Iterable<Bson>): Bson = Filters.and(value + this)

    infix fun Bson.or(value: Bson): Bson = Filters.or(this, value)
    fun Bson.or(vararg value: Bson): Bson = Filters.or(this, *value)
    infix fun Bson.or(value: Iterable<Bson>): Bson = Filters.or(value + this)

    fun not(value: Bson): Bson = Filters.not(value)

    infix fun Bson.nor(value: Bson): Bson = Filters.nor(this, value)
    fun Bson.nor(vararg value: Bson): Bson = Filters.nor(this, *value)
    infix fun Bson.nor(value: Iterable<Bson>): Bson = Filters.nor(value + this)

    fun <V> KProperty1<T, V>.exists(): Bson = Filters.exists(name, true)
    fun <V> KProperty1<T, V>.doesNotExist(): Bson = Filters.exists(name, false)

    infix fun <V> KProperty1<T, V>.typed(type: BsonType): Bson = Filters.type(name, type)
    infix fun <V> KProperty1<T, V>.typed(type: String): Bson = Filters.type(name, type)

    fun <V> KProperty1<T, V>.mod(divisor: Long, remainder: Long): Bson = Filters.mod(name, divisor, remainder)

    infix fun <V> KProperty1<T, V>.matches(pattern: String): Bson = Filters.regex(name, pattern)
    fun <V> KProperty1<T, V>.matches(pattern: String, options: String): Bson = Filters.regex(name, pattern, options)
    fun <V> KProperty1<T, V>.matches(pattern: Pattern): Bson = Filters.regex(name, pattern)
    fun <V> KProperty1<T, V>.matches(pattern: Regex): Bson = Filters.regex(name, pattern.toPattern())

    fun text(search: String, options: TextSearchOptions = TextSearchOptions()): Bson = Filters.text(search, options)

    fun where(jsExpression: String): Bson = Filters.where(jsExpression)

    fun <V> expr(expression: V & Any): Bson = Filters.expr(expression)

    infix fun <V> KProperty1<T, V>.all(value: Iterable<V>): Bson = Filters.all(name, value)
    fun <V> KProperty1<T, V>.all(vararg value: V): Bson = Filters.all(name, value)

    infix fun <V> KProperty1<T, V>.elemMatch(filter: Bson): Bson = Filters.elemMatch(name, filter)

    infix fun <V> KProperty1<T, V>.size(size: Int): Bson = Filters.size(name, size)

    infix fun <V> KProperty1<T, V>.bitsAllClear(bitmask: Long): Bson = Filters.bitsAllClear(name, bitmask)
    infix fun <V> KProperty1<T, V>.bitsAllSet(bitmask: Long): Bson = Filters.bitsAllSet(name, bitmask)
    infix fun <V> KProperty1<T, V>.bitsAnyClear(bitmask: Long): Bson = Filters.bitsAnyClear(name, bitmask)
    infix fun <V> KProperty1<T, V>.bitsAnySet(bitmask: Long): Bson = Filters.bitsAnySet(name, bitmask)

    infix fun <V> KProperty1<T, V>.geoWithin(geometry: Geometry): Bson = Filters.geoWithin(name, geometry)
    infix fun <V> KProperty1<T, V>.geoWithin(geometry: Bson): Bson = Filters.geoWithin(name, geometry)

    fun <V> KProperty1<T, V>.geoWithinBox(
        lowerLeftX: Double, lowerLeftY: Double, upperRightX: Double, upperRightY: Double
    ): Bson = Filters.geoWithinBox(name, lowerLeftX, lowerLeftY, upperRightX, upperRightY)

    fun <V> KProperty1<T, V>.geoWithinPolygon(points: List<List<Double>>): Bson = Filters.geoWithinPolygon(name, points)

    fun <V> KProperty1<T, V>.geoWithinCenter(x: Double, y: Double, radius: Double, spherical: Boolean): Bson =
        when (spherical) {
            false -> Filters.geoWithinCenter(name, x, y, radius)
            true -> Filters.geoWithinCenterSphere(name, x, y, radius)
        }

    infix fun <V> KProperty1<T, V>.geoIntersects(geometry: Bson): Bson = Filters.geoIntersects(name, geometry)
    infix fun <V> KProperty1<T, V>.geoIntersects(geometry: Geometry): Bson = Filters.geoIntersects(name, geometry)

    fun <V> KProperty1<T, V>.near(
        geometry: Point,
        spherical: Boolean,
        maxDistance: Double? = null,
        minDistance: Double? = null
    ): Bson =
        when (spherical) {
            false -> Filters.near(name, geometry, maxDistance, minDistance)
            true -> Filters.nearSphere(name, geometry, maxDistance, minDistance)
        }

    fun <V> KProperty1<T, V>.near(
        geometry: Bson,
        spherical: Boolean,
        maxDistance: Double? = null,
        minDistance: Double? = null,
    ): Bson =
        when (spherical) {
            false -> Filters.near(name, geometry, maxDistance, minDistance)
            true -> Filters.nearSphere(name, geometry, maxDistance, minDistance)
        }

    fun <V> KProperty1<T, V>.near(
        x: Double, y: Double, spherical: Boolean, maxDistance: Double? = null, minDistance: Double? = null
    ): Bson = when (spherical) {
        false -> Filters.near(name, x, y, maxDistance, minDistance)
        true -> Filters.nearSphere(name, x, y, maxDistance, minDistance)
    }

    fun jsonSchema(schema: Bson): Bson = Filters.jsonSchema(schema)

    fun empty(): Bson = Filters.empty()
}