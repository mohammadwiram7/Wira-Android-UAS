package com.mohammadwiram.wiraandroiduas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohammadwiram.wiraandroiduas.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Avengers"

        // memanggil semua fungsi
        setupRecyclerView()
        getDataFromApi()
    }

    // menyiapkan recyclerview dan intent
    // pada saat salah 1 data di pilih akan masuk ke DetailActivity
    // di DetailActivity data yang di tampilkan berupa gambar dan judul
    private fun setupRecyclerView(){
        movieAdapter = MovieAdapter(arrayListOf(), object : MovieAdapter.OnAdapterListener {
            override fun onClick(result: MainModel.Result) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java)
                        .putExtra("intent_title", result.title)
                        .putExtra("intent_image", result.image)
                )
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    // mendapatkan data api yang telah di definisikn pada file ApiService
    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<MainModel> {
                override fun onFailure(call: Call<MainModel>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<MainModel>,
                    response: Response<MainModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }
                }
            })
    }

    // banyak digunakan pada saat melakukan test api
    // seperti apakah data api bisa tampil
    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    // menampilkan loading pada saat data di proses
    // untuk ditampilkan
    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    // menampilkan data api pada recyclerview
    private fun showResult(results: MainModel) {
        for (result in results.result) printLog( "title: ${result.title}" )
        movieAdapter.setData( results.result )
    }
}
