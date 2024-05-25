package dev.stashy.mongoservices.builders

import com.mongodb.client.model.Projections
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

class ProjectionBuilder<T> {
    fun include(vararg fields: KProperty1<T, *>): Bson =
        Projections.include(fields.map { it.serialName() })

    fun include(fields: Iterable<KProperty1<T, *>>): Bson =
        Projections.include(fields.map { it.serialName() })

    fun exclude(vararg fields: KProperty1<T, *>): Bson =
        Projections.exclude(fields.map { it.serialName() })

    fun exclude(fields: Iterable<KProperty1<T, *>>): Bson =
        Projections.exclude(fields.map { it.serialName() })

    fun excludeId(): Bson = Projections.excludeId()

    fun elemMatch(field: KProperty1<T, *>): Bson =
        Projections.elemMatch(field.serialName())

    fun elemMatch(field: KProperty1<T, *>, filter: FilterBuilder<T>.() -> Bson): Bson =
        Projections.elemMatch(field.serialName(), filter(FilterBuilder()))

    fun meta(field: KProperty1<T, *>, metaFieldName: String): Bson =
        Projections.meta(field.serialName(), metaFieldName)

    fun metaTextScore(field: KProperty1<T, *>): Bson =
        Projections.metaTextScore(field.serialName())

    fun metaSearchScore(field: KProperty1<T, *>): Bson =
        Projections.metaSearchScore(field.serialName())

    fun metaVectorSearchScore(field: KProperty1<T, *>): Bson =
        Projections.metaVectorSearchScore(field.serialName())

    fun metaSearchHighlights(field: KProperty1<T, *>): Bson =
        Projections.metaSearchHighlights(field.serialName())

    fun slice(field: KProperty1<T, *>, skip: Int = 0, limit: Int): Bson =
        Projections.slice(field.serialName(), skip, limit)

    fun combine(vararg projection: Bson): Bson =
        Projections.fields(*projection)

    fun combine(projections: List<Bson>): Bson =
        Projections.fields(projections)

    fun <TExpression> computed(field: KProperty1<T, *>, expression: TExpression & Any) =
        Projections.computed(field.serialName(), expression)

    fun computedSearchMeta(field: KProperty1<T, *>): Bson =
        Projections.computedSearchMeta(field.serialName())
}