package com.elish.terminal.data

import retrofit2.http.GET

interface ApiService {

    @GET("aggs/ticker/AAPL/range/1/hour/2022-01-09/2023-01-09?adjusted=true&sort=asc&limit=50000&apiKey=vv25ipCpwwaVewSXVKJ9cTC8mZkOoT5M")
    suspend fun loadBars(): Result
}