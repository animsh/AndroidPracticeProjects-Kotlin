package com.animsh.e_commercevendor.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.animsh.e_commercetest.utils.LoadingDialog
import com.animsh.e_commercevendor.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "TAGTAGTAG"
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    lateinit var ref: StorageReference
    lateinit var loadingDialog: LoadingDialog

    private var productImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            loadingDialog = LoadingDialog(this@MainActivity)
            storage = FirebaseStorage.getInstance();
            storageReference = storage.reference;

            uploadImage1.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(
                        intent,
                        "Select Image from here..."
                    ),
                    PICK_IMAGE_REQUEST
                )
            }

            uploadImage2.setOnClickListener {
                uploadImage1.performClick()
            }

            uploadImage3.setOnClickListener {
                uploadImage1.performClick()
            }



            uploadBtn.setOnClickListener {
                loadingDialog.showLoadingDialog()
                val category = inputCategory.text.toString()
                val productName = inputProductName.text.toString()
                val productPrice = inputPrice.text.toString()
                val productGST = inputGST.text.toString()
                val productDeliveryCharge = inputDeliveryCharge.text.toString()
                val productOffer = inputOffer.text.toString()
                if (category.isEmpty() or productName.isEmpty() or productPrice.isEmpty() or productGST.isEmpty() or productDeliveryCharge.isEmpty() or productOffer.isEmpty() or (productImage == "")) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please fill all the information! if forgot upload image...",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingDialog.dismissDialog()
                    return@setOnClickListener
                }

                val product = hashMapOf(
                    "product_category" to category.toString(),
                    "product_delivery_charge" to productDeliveryCharge.toString(),
                    "product_gst" to productGST.toString(),
                    "product_image" to productImage,
                    "product_name" to productName.toString(),
                    "product_offer" to productOffer.toString(),
                    "product_price" to productPrice.toString(),

                    )

                FirebaseFirestore.getInstance().collection("products").document()
                    .set(product)
                    .addOnSuccessListener {
                        Toast
                            .makeText(
                                this@MainActivity,
                                "Uploaded!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        loadingDialog.dismissDialog()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error writing document", e)
                        Toast.makeText(
                            this@MainActivity,
                            e.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        loadingDialog.dismissDialog()
                    }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            ref = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    val downloadUri: Task<Uri> = it.storage.downloadUrl
                    downloadUri.addOnSuccessListener { uri ->
                        Log.d(
                            TAG, "uploadImage: $uri"
                        )
                        Toast
                            .makeText(
                                this@MainActivity,
                                "Image Uploaded!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        productImage = downloadUri.result.toString()
                    }

                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@MainActivity,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = (100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }
}