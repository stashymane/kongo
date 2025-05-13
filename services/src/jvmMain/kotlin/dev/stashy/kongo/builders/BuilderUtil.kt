package dev.stashy.kongo.builders

import kotlinx.serialization.SerialName
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

internal fun <T, V> KProperty1<T, V>.serialName(): String =
    findAnnotation<SerialName>()?.value ?: this.name

internal fun <T, V> T.fieldToValue(property: KProperty1<T, V>) = property to property.get(this)
