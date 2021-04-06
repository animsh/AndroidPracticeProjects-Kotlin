package com.animsh.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by animsh on 4/6/2021.
 */
@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItems)

    @Delete
    suspend fun delete(item: ShoppingItems)

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItems>>
}