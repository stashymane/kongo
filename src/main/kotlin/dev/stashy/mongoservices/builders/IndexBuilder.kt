package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Indexes
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class IndexBuilder<T> {
    fun <V> KProperty1<T, V>.ascending(): Bson = Indexes.ascending(serialName())
    fun <V> Iterable<KProperty1<T, V>>.ascending(): Bson = Indexes.ascending(map { it.serialName() })

    fun <V> KProperty1<T, V>.descending(): Bson = Indexes.descending(serialName())
    fun <V> Iterable<KProperty1<T, V>>.descending(): Bson = Indexes.descending(map { it.serialName() })

    fun <V> KProperty1<T, V>.geo2d(): Bson = Indexes.geo2d(serialName())
    fun <V> Iterable<KProperty1<T, V>>.geo2dSphere(): Bson = Indexes.geo2dsphere(map { it.serialName() })

    fun text(): Bson = Indexes.text()
    fun <V> KProperty1<T, V>.text(): Bson = Indexes.text(serialName())

    fun <V> KProperty1<T, V>.hashed(): Bson = Indexes.hashed(serialName())

    fun <V : Bson> compound(indexes: List<V>): Bson = Indexes.compoundIndex(indexes)
    fun compound(vararg indexes: Bson): Bson = Indexes.compoundIndex(*indexes)
}