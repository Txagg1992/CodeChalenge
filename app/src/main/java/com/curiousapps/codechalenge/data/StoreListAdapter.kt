package com.curiousapps.codechalenge.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.curiousapps.codechalenge.R
import com.curiousapps.codechalenge.activities.DetailActivity
import com.curiousapps.codechalenge.model.Store
import com.squareup.picasso.Picasso

class StoreListAdapter(
    private val list: ArrayList<Store>,
    private val context: Context): RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {

    private val EXTRA_URL: String = "logoUrl"
    private val EXTRA_STORE_NAME: String = "storeName"
    private val EXTRA_ADDRESS: String = "address"
    private val EXTRA_CITY: String = "city"
    private val EXTRA_STATE: String = "state"
    private val EXTRA_ZIPCODE: String = "zipcode"
    private val EXTRA_PHONE: String = "phone"

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_list_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var storeName = itemView.findViewById<TextView>(R.id.store_name)
        var storeAddress = itemView.findViewById<TextView>(R.id.store_address)
        var storeCity = itemView.findViewById<TextView>(R.id.store_city)
        var storeState = itemView.findViewById<TextView>(R.id.store_state)
        var storeZipcode = itemView.findViewById<TextView>(R.id.store_zipcode)
        var storePhone = itemView.findViewById<TextView>(R.id.store_phone)
        var storeLogo = itemView.findViewById<ImageView>(R.id.store_logo_image)

        fun bindView(store: Store){
            storeName.text = store.mStoreName
            storeAddress.text = store.mAddress
            storeCity.text = store.mCity
            storeState.text = store.mState
            storeZipcode.text = store.mZipcode.toString()
            storePhone.text = store.mPhoneNumber

            if (!TextUtils.isEmpty(store.mStoreLogoUrl)){
                Picasso.get()
                    .load(store.mStoreLogoUrl)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(storeLogo)
            }else{
                Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .into(storeLogo)
            }

            storeLogo.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                val clickedItem :Store = list[adapterPosition]

                intent.putExtra(EXTRA_URL, clickedItem.mStoreLogoUrl)
                intent.putExtra(EXTRA_STORE_NAME, clickedItem.mStoreName)
                intent.putExtra(EXTRA_ADDRESS, clickedItem.mAddress)
                intent.putExtra(EXTRA_CITY, clickedItem.mCity)
                intent.putExtra(EXTRA_STATE, clickedItem.mState)
                intent.putExtra(EXTRA_ZIPCODE, clickedItem.mZipcode)
                intent.putExtra(EXTRA_PHONE, clickedItem.mPhoneNumber)
                context.startActivity(intent)
            }


        }
    }

}