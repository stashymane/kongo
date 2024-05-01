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
    infix fun <V> KProperty1<T, V>.equals(value: V): Bson = Filters.eq(serialName(), value)
    infix fun <V> KProperty1<T, V>.notEquals(value: V): Bson = Filters.ne(serialName(), value)

    infix fun <V> KProperty1<T, V>.greaterThan(value: V & Any): Bson = Filters.gt(serialName(), value)
    infix fun <V> KProperty1<T, V>.lessThan(value: V & Any): Bson = Filters.lt(serialName(), value)

    infix fun <V> KProperty1<T, V>.greaterOrEquals(value: V & Any): Bson = Filters.gte(serialName(), value)
    infix fun <V> KProperty1<T, V>.lessOrEquals(value: V & Any): Bson = Filters.lte(serialName(), value)

    infix fun <V> KProperty1<T, V>.containedIn(value: Iterable<V>): Bson = Filters.`in`(serialName(), value)
    fun <V> KProperty1<T, V>.containedIn(vararg value: V): Bson = Filters.`in`(serialName(), *value)

    infix fun <V> KProperty1<T, V>.notContainedIn(value: Iterable<V>): Bson = Filters.nin(serialName(), value)
    fun <V> KProperty1<T, V>.notContainedIn(vararg value: V): Bson = Filters.nin(serialName(), *value)

    fun and(vararg values: Bson): Bson = Filters.and(*values)
    fun and(values: List<Bson>): Bson = Filters.and(values)
    infix fun Bson.and(value: Bson): Bson = Filters.and(this, value)
    fun Bson.and(vararg value: Bson): Bson = Filters.and(this, *value)
    infix fun Bson.and(value: Iterable<Bson>): Bson = Filters.and(value + this)

    fun or(vararg values: Bson): Bson = Filters.or(*values)
    fun or(values: List<Bson>): Bson = Filters.or(values)
    infix fun Bson.or(value: Bson): Bson = Filters.or(this, value)
    fun Bson.or(vararg value: Bson): Bson = Filters.or(this, *value)
    infix fun Bson.or(value: Iterable<Bson>): Bson = Filters.or(value + this)

    fun not(value: Bson): Bson = Filters.not(value)

    fun nor(vararg values: Bson): Bson = Filters.nor(*values)
    fun nor(values: List<Bson>): Bson = Filters.nor(values)
    infix fun Bson.nor(value: Bson): Bson = Filters.nor(this, value)
    fun Bson.nor(vararg value: Bson): Bson = Filters.nor(this, *value)
    infix fun Bson.nor(value: Iterable<Bson>): Bson = Filters.nor(value + this)

    fun <V> KProperty1<T, V>.exists(): Bson = Filters.exists(serialName(), true)
    fun <V> KProperty1<T, V>.doesNotExist(): Bson = Filters.exists(serialName(), false)

    infix fun <V> KProperty1<T, V>.typed(type: BsonType): Bson = Filters.type(serialName(), type)
    infix fun <V> KProperty1<T, V>.typed(type: String): Bson = Filters.type(serialName(), type)

    fun <V> KProperty1<T, V>.mod(divisor: Long, remainder: Long): Bson = Filters.mod(serialName(), divisor, remainder)

    infix fun <V> KProperty1<T, V>.regexMatch(pattern: String): Bson = Filters.regex(serialName(), pattern)
    fun <V> KProperty1<T, V>.regexMatch(pattern: String, options: String): Bson =
        Filters.regex(serialName(), pattern, options)

    fun <V> KProperty1<T, V>.regexMatch(pattern: Pattern): Bson = Filters.regex(serialName(), pattern)
    fun <V> KProperty1<T, V>.regexMatch(pattern: Regex): Bson = Filters.regex(serialName(), pattern.toPattern())

    fun text(search: String, options: TextSearchOptions = TextSearchOptions()): Bson = Filters.text(search, options)

    fun where(jsExpression: String): Bson = Filters.where(jsExpression)

    fun <V> expr(expression: V & Any): Bson = Filters.expr(expression)

    infix fun <V> KProperty1<T, V>.all(value: Iterable<V>): Bson = Filters.all(serialName(), value)
    fun <V> KProperty1<T, V>.all(vararg value: V): Bson = Filters.all(serialName(), value)

    infix fun <V> KProperty1<T, V>.contains(filter: Bson): Bson = Filters.elemMatch(serialName(), filter)

    infix fun <V> KProperty1<T, V>.isSizeOf(size: Int): Bson = Filters.size(serialName(), size)

    infix fun <V> KProperty1<T, V>.bitsAllClear(bitmask: Long): Bson = Filters.bitsAllClear(serialName(), bitmask)
    infix fun <V> KProperty1<T, V>.bitsAllSet(bitmask: Long): Bson = Filters.bitsAllSet(serialName(), bitmask)
    infix fun <V> KProperty1<T, V>.bitsAnyClear(bitmask: Long): Bson = Filters.bitsAnyClear(serialName(), bitmask)
    infix fun <V> KProperty1<T, V>.bitsAnySet(bitmask: Long): Bson = Filters.bitsAnySet(serialName(), bitmask)

    infix fun <V> KProperty1<T, V>.geoWithin(geometry: Geometry): Bson = Filters.geoWithin(serialName(), geometry)
    infix fun <V> KProperty1<T, V>.geoWithin(geometry: Bson): Bson = Filters.geoWithin(serialName(), geometry)

    fun <V> KProperty1<T, V>.geoWithinBox(
        lowerLeftX: Double, lowerLeftY: Double, upperRightX: Double, upperRightY: Double
    ): Bson = Filters.geoWithinBox(serialName(), lowerLeftX, lowerLeftY, upperRightX, upperRightY)

    fun <V> KProperty1<T, V>.geoWithinPolygon(points: List<List<Double>>): Bson =
        Filters.geoWithinPolygon(serialName(), points)

    fun <V> KProperty1<T, V>.geoWithinCenter(x: Double, y: Double, radius: Double, spherical: Boolean): Bson =
        when (spherical) {
            false -> Filters.geoWithinCenter(serialName(), x, y, radius)
            true -> Filters.geoWithinCenterSphere(serialName(), x, y, radius)
        }

    infix fun <V> KProperty1<T, V>.geoIntersects(geometry: Bson): Bson = Filters.geoIntersects(serialName(), geometry)
    infix fun <V> KProperty1<T, V>.geoIntersects(geometry: Geometry): Bson =
        Filters.geoIntersects(serialName(), geometry)

    fun <V> KProperty1<T, V>.near(
        geometry: Point,
        spherical: Boolean,
        maxDistance: Double? = null,
        minDistance: Double? = null
    ): Bson =
        when (spherical) {
            false -> Filters.near(serialName(), geometry, maxDistance, minDistance)
            true -> Filters.nearSphere(serialName(), geometry, maxDistance, minDistance)
        }

    fun <V> KProperty1<T, V>.near(
        geometry: Bson,
        spherical: Boolean,
        maxDistance: Double? = null,
        minDistance: Double? = null,
    ): Bson =
        when (spherical) {
            false -> Filters.near(serialName(), geometry, maxDistance, minDistance)
            true -> Filters.nearSphere(serialName(), geometry, maxDistance, minDistance)
        }

    fun <V> KProperty1<T, V>.near(
        x: Double, y: Double, spherical: Boolean, maxDistance: Double? = null, minDistance: Double? = null
    ): Bson = when (spherical) {
        false -> Filters.near(serialName(), x, y, maxDistance, minDistance)
        true -> Filters.nearSphere(serialName(), x, y, maxDistance, minDistance)
    }

    fun jsonSchema(schema: Bson): Bson = Filters.jsonSchema(schema)

    fun empty(): Bson = Filters.empty()
}