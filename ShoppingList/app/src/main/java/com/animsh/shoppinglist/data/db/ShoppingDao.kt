package com.animsh.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.animsh.shoppinglist.data.db.entities.ShoppingItems

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