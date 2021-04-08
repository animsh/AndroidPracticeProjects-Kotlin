package com.animsh.shoppinglist.ui.shoppinglist

import com.animsh.shoppinglist.data.db.entities.ShoppingItems

/**
 * Created by animsh on 4/8/2021.
 */
interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItems)
}