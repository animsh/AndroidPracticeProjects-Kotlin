package com.animsh.shoppinglist.data.repositories

import com.animsh.shoppinglist.data.db.ShoppingDatabase
import com.animsh.shoppinglist.data.db.entities.ShoppingItems

/**
 * Created by animsh on 4/7/2021.
 */
class ShoppingRepository(
    private val db: ShoppingDatabase
) {

    suspend fun upsert(item: ShoppingItems) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: ShoppingItems) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()
}