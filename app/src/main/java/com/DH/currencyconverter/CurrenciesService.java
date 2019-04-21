package com.DH.currencyconverter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrenciesService {
    @GET("latest")
    Call<Currency> getCurrencies(@Query("access_key") String accessKey);
}
