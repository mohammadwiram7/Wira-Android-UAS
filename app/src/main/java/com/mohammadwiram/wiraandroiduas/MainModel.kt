package com.mohammadwiram.wiraandroiduas

data class MainModel (
    val result: ArrayList<Result>
) {
    // dengan membuat model pada saat menampilkan data api
    // jauh lebih mudah. data model disesuaikan dengan output
    // json yang dihasilkan
    data class Result (val id: Int, val title: String, val image: String)
}