package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Indexes
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class IndexBuilder<T> {
    fun <V> KProperty1<T, V>.ascending(): Bson = Indexes.ascending(name)
    fun <V> Iterable<KProperty1<T, V>>.ascending(): Bson = Indexes.ascending(map { it.name })

    fun <V> KProperty1<T, V>.descending(): Bson = Indexes.descending(name)
    fun <V> Iterable<KProperty1<T, V>>.descending(): Bson = Indexes.descending(map { it.name })

    fun <V> KProperty1<T, V>.geo2d(): Bson = Indexes.geo2d(name)
    fun <V> Iterable<KProperty1<T, V>>.geo2dSphere(): Bson = Indexes.geo2dsphere(map { it.name })

    fun text(): Bson = Indexes.text()
    fun <V> KProperty1<T, V>.text(): Bson = Indexes.text(name)

    fun <V> KProperty1<T, V>.hashed(): Bson = Indexes.hashed(name)

    fun <V : Bson> compound(indexes: List<V>): Bson = Indexes.compoundIndex(indexes)
    fun compound(vararg indexes: Bson): Bson = Indexes.compoundIndex(*indexes)
}