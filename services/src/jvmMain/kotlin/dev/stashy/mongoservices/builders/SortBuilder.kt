package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Sorts
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class SortBuilder<T> {
    fun <V> KProperty1<T, V>.ascending(): Bson = Sorts.ascending(serialName())
    fun <V> Iterable<KProperty1<T, V>>.ascending(): Bson = Sorts.ascending(map { it.serialName() })

    fun <V> KProperty1<T, V>.descending(): Bson = Sorts.descending(serialName())
    fun <V> Iterable<KProperty1<T, V>>.descending(): Bson = Sorts.descending(map { it.serialName() })

    fun <V> KProperty1<T, V>.metaTextScore(): Bson = Sorts.metaTextScore(serialName())

    fun compound(vararg sorts: Bson): Bson = Sorts.orderBy(*sorts)
    fun <V : Bson> compound(sorts: List<V>): Bson = Sorts.orderBy(sorts)
}