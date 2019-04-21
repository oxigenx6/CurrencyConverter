package com.DH.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Double rub;
    TextView res = findViewById(R.id.result);
    TextView cur = findViewById(R.id.cur);
    EditText num = findViewById(R.id.inputNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRetrofit();
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://data.fixer.io/api/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        CurrenciesService service = retrofit.create(CurrenciesService.class);
        Call<Currency> request = service.getCurrencies("0c258f3c871d675df28a932cff02f025");
        request.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                rub = response.body().rates.get("RUB");
                cur.setText(Double.toString(rub));
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {

            }
        });
    }

    void on
}
