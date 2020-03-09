package com.curiousapps.codechalenge.activities

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.curiousapps.codechalenge.R
import com.curiousapps.codechalenge.data.StoreListAdapter
import com.curiousapps.codechalenge.model.Store
import com.curiousapps.codechalenge.model.storeUrl
import kotlinx.android.synthetic.main.activity_store_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class StoreListActivity : AppCompatActivity() {

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
}
