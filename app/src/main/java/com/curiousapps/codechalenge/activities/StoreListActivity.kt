package com.curiousapps.codechalenge.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.curiousapps.codechalenge.R
import com.curiousapps.codechalenge.data.AppController
import com.curiousapps.codechalenge.data.StoreListAdapter
import com.curiousapps.codechalenge.model.Store
import com.curiousapps.codechalenge.model.storeUrl
import kotlinx.android.synthetic.main.activity_store_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class StoreListActivity : AppCompatActivity() {

    private lateinit var urlJet: String
    private var mProgressBar: ProgressBar? = null
    private var volleyRequest: RequestQueue? = null
    private var storeArray: ArrayList<Store>? = null
    private var mStoreAdapter: StoreListAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_list)

        volleyRequest = Volley.newRequestQueue(this)

        getStores(storeUrl);

        if (!isOnline()){
            Toast.makeText(
                this, "The device is not connected to a network", Toast.LENGTH_LONG).show()
            }else{
            checkCache(storeUrl)
        }
        checkCache(storeUrl)
    }

    private fun checkCache(Url: String){

        val cache: Cache? = AppController.instance?.requestQueue?.cache
        val imageLoader = AppController.instance?.imageLoader
        urlJet = storeUrl
        val entry = cache?.get(urlJet)
        if (entry != null){
            try {
                val data = entry.data.let { String(entry.data) }
            }catch (e:UnsupportedEncodingException){
                e.printStackTrace()
            }
        }else{
            getStores(urlJet)
        }
    }

    private fun getStores(Url: String){

            storeArray = ArrayList<Store>()

            val storeRequest = JsonObjectRequest(Request.Method.GET, Url, null,
                Response.Listener { response: JSONObject ->
                    try {
                        val resultArray: JSONArray = response.getJSONArray("stores")
                        for (i in 0 until resultArray.length()) {
                            val storeObj: JSONObject = resultArray.optJSONObject(i)

                            val storeName = storeObj.getString("name")
                            val storeAddress = storeObj.getString("address")
                            val storeCity = storeObj.getString("city")
                            val storeState = storeObj.getString("state")
                            val storeZipCode = storeObj.getInt("zipcode")
                            val storePhone = storeObj.getString("phone")
                            val storeLogoUrl = storeObj.getString("storeLogoURL")
                            val storeID = storeObj.getInt("storeID")

                            Log.d("<==Result==>", storeName)
                            Log.d("<**State**>", storeLogoUrl)

                            val store = Store()
                            store.mStoreName = storeName
                            store.mAddress = storeAddress
                            store.mCity = "$storeCity, "
                            store.mState = storeState
                            store.mZipcode = storeZipCode
                            store.mPhoneNumber = storePhone
                            store.mStoreLogoUrl = storeLogoUrl
                            store.mStoreId = storeID

                            storeArray?.add(store)
                            mStoreAdapter = StoreListAdapter(storeArray!!, this)
                            mLayoutManager = LinearLayoutManager(this)

                            setupRecyclerView()
                        }

                        mStoreAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error: VolleyError ->
                    try {
                        Log.d(" **Volley Error **", error.toString())
                    } catch (ex: JSONException) {
                        ex.printStackTrace()
                    }
                })
            volleyRequest?.add(storeRequest)
    }

    private fun setupRecyclerView(){
        store_recycler_view.layoutManager = mLayoutManager
        store_recycler_view.adapter = mStoreAdapter
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}
