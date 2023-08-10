package com.oztasibrahimomer.retrofitcompose3.service

import com.oztasibrahimomer.retrofitcompose3.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //

//  atilsamancioglu/K21-JSONDataSet/master/crypto.json


    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

    fun getData():Call<List<CryptoModel>>
}