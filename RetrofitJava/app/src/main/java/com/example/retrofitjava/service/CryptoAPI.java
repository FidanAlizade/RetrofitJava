package com.example.retrofitjava.service;

import io.reactivex.Observable;

import com.example.retrofitjava.model.CryptoModel;

import java.util.List;

import retrofit2.http.GET;

public interface CryptoAPI {

    //GET, POST, UPDATE, DELETE

//https://api.nomics.com/v1/currencies/ticker?key=6e0c9a1b3740ea0e708e27ad0f2c90827ee36e19
    //Base URL https://api.nomics.com/v1/
    //GET -> currencies/ticker?key=6e0c9a1b3740ea0e708e27ad0f2c90827ee36e19

    //servise get ile bir request yolla, sonra bir getData metodu olustur call etsin ve list formada modeli gondersin
    @GET("currencies/ticker?key=6e0c9a1b3740ea0e708e27ad0f2c90827ee36e19")
    Observable<List<CryptoModel>> getData();



    // Call<List<CryptoModel>> getData();
}
