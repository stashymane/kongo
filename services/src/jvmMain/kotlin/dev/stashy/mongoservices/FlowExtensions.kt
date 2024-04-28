package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.FindFlow
import dev.stashy.mongoservices.builders.SortBuilder
import org.bson.conversions.Bson

fun <T : Any> FindFlow<T>.sort(sort: SortBuilder<T>.() -> Bson) = sort(sort(SortBuilder()))
