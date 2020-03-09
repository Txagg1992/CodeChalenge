package com.curiousapps.codechalenge.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.codechalenge.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailExtras = intent.extras
        if (detailExtras != null){
            val storeLogoDetail = detailExtras.getString("logoUrl")
            val storeDetailName = detailExtras.getString("storeName")
            val storeDetailAddress = detailExtras.getString("address")
            val storeDetailCity = detailExtras.getString("city")
            val storeDetailState = detailExtras.getString("state")
            val storeDetailZipCode = detailExtras.getInt("zipcode")
            val storeDetailPhone = detailExtras.getString("phone")

            text_view_name_detail.text = storeDetailName
            text_view_address_detail.text = storeDetailAddress
            text_view_city_detail.text = storeDetailCity
            text_view_state_detail.text = storeDetailState
            text_view_zip_detail.text = storeDetailZipCode.toString()
            text_view_phone_detail.text = storeDetailPhone

            if (!TextUtils.isEmpty(storeLogoDetail)){
                Picasso.get()
                    .load(storeLogoDetail)
                    .into(image_view_store_logo_detail)
            }else{
                Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .into(image_view_store_logo_detail)
            }
        }

        text_view_phone_detail.setOnClickListener{

            val intent  = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${detailExtras?.getString("phone")}")
            startActivity(intent)
        }
    }
}
