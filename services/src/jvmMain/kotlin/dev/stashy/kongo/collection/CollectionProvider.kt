package dev.stashy.kongo.collection

import com.mongodb.kotlin.client.coroutine.MongoCollection

interface CollectionProvider<T : Any> {
    val collection: MongoCollection<T>
}
