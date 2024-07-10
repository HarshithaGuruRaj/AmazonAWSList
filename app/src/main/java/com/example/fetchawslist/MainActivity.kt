package com.example.fetchawslist

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class MainActivity : AppCompatActivity() {


    //  https://fetch-hiring.s3.amazonaws.com/hiring.json
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetched")
        progressDialog.show()

        RetrofitInstance.apiInterface.getListItems().enqueue(object : Callback<List<ResponseDataListItem>> {
            override fun onResponse(
                call: Call<List<ResponseDataListItem>>,
                response: Response<List<ResponseDataListItem>>
            ) {
              progressDialog.dismiss()
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        val filteredSortedItems = items
                            .filter { !it.name.isNullOrBlank() }
                            .sortedWith(compareBy({ it.listId }, { it.name }))
                            .groupBy { it.listId }

                        itemAdapter = ItemAdapter(filteredSortedItems)
                        recyclerView.adapter = itemAdapter
                    } else {
                        Log.d("API_RESPONSE", "No data found")
                    }
                } else {
                    Log.d("API_RESPONSE", "Response not successful")
                }
           }

           override fun onFailure(call: Call<List<ResponseDataListItem>>, t: Throwable) {
               Toast.makeText(this@MainActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT)
                   .show()
               progressDialog.dismiss()
               Log.e("API_RESPONSE", "Error: ${t.localizedMessage}")
           }
       })
    }
}