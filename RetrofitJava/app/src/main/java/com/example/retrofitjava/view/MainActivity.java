package com.example.retrofitjava.view;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitjava.adapter.RecyclerViewAdapter;
import com.example.retrofitjava.databinding.ActivityMainBinding;
import com.example.retrofitjava.model.CryptoModel;
import com.example.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
//Datalari yukleme
  ArrayList<CryptoModel> cryptoModelArrayList;
  final String BASE_URL = "https://api.nomics.com/v1/";
  Retrofit retrofit;

  private ActivityMainBinding binding;

  RecyclerViewAdapter recyclerViewAdapter;
  
  CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //https://api.nomics.com/v1/currencies/ticker?key=6e0c9a1b3740ea0e708e27ad0f2c90827ee36e19

    //Retrofit & JSON
        //Gson-u aliyor (setLenient) ve olusturuyor (create)
        Gson gson = new GsonBuilder().setLenient().create();

      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .addConverterFactory(GsonConverterFactory.create(gson))
              .build();

      getDataFromAPI();
    }

    public void getDataFromAPI(){

        //veriyi APIdan alma islemi
        //CryptoAPI nin objesini olusturuyorz, sonra call olusturup, cryptoAPI objesinden getData metodumuzla veriyi aliyoruz
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

         compositeDisposable = new CompositeDisposable(cryptoAPI.getData()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(this::handleResponse));

         /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();


        //enquqeue- asychronous sekilde(main threadi bloklamadan) request edib call backi almamizi temin edir
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    //response  dan elde etdiyimiz listi bizim arrayliste qoyduq
                    cryptoModelArrayList = new ArrayList<>(responseList);

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModelArrayList);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);

//                    for(CryptoModel cryptoModel: cryptoModelArrayList){
//                        System.out.println(cryptoModel.currency);
//                        System.out.println(cryptoModel.price);
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

         */


    }

    public void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModelArrayList = new ArrayList<>(cryptoModelList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModelArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
       
    }
}