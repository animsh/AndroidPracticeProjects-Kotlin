package com.animsh.e_commercetest.adapters

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


/**
 * Created by animsh on 4/21/2021.
 */
class ItemBindingAdapter {
    companion object {

        @BindingAdapter("android:loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView, imageUrl: String) {
            try {
                Glide.with(imageView.context).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            } catch (ignored: Exception) {
            }
        }

        @BindingAdapter("android:loadPrice", "android:loadOffer", requireAll = true)
        @JvmStatic
        fun loadPrice(textView: TextView, price: String, offer: String) {
            val offerInt: Int = offer.toInt()
            val priceInt: Int = price.toInt()

            val offerPrice: Int = (priceInt * offerInt) / 100
            textView.text = "₹${priceInt - offerPrice}"
        }

        @BindingAdapter("android:loadOriginalPrice")
        @JvmStatic
        fun loadOriginalPrice(textView: TextView, price: String) {
            textView.text = "₹$price"
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        @BindingAdapter("android:offer")
        @JvmStatic
        fun offer(textView: TextView, offer: String){
            textView.text = "$offer% off"
        }
    }
}