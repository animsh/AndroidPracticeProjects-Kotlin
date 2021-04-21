package com.animsh.e_commercetest.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.e_commercetest.R
import com.animsh.e_commercetest.databinding.ActivityMainBinding
import com.animsh.e_commercetest.entities.Product
import com.animsh.e_commercetest.ui.auth.AuthActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore
    private val TAG = "TAGTAGTAG"
    private val mAdapter by lazy { ProductsAdapter(this) }
    private var products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.apply {
            getAllDocs()
            recyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, true)
            recyclerView.adapter = mAdapter

            signOut.setOnClickListener {
                saveLoginState()
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun getAllDocs() {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    products.add(
                        Product(
                            document.getString("product_category").toString(),
                            document.getString("product_delivery_charge").toString(),
                            document.getString("product_gst").toString(),
                            document.getString("product_image").toString(),
                            document.getString("product_name").toString(),
                            document.getString("product_offer").toString(),
                            document.getString("product_price").toString()
                        )
                    )
                    Log.d(TAG, "${document.id} => ${document.data} ${products.size}")
                }
                mAdapter.setData(products)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun saveLoginState() {
        val sharedPref =
            this.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        with(sharedPref?.edit()) {
            this?.putBoolean("userLogin", false)
            this?.apply()
        }
    }

}