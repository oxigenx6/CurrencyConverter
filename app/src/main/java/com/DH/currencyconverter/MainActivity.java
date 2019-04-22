package com.DH.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Double rub;
    TextView res;
    TextView cur;
    EditText num;
    Button errorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = findViewById(R.id.result);
        cur = findViewById(R.id.cur);
        num = findViewById(R.id.inputNum);
        errorButton = findViewById(R.id.errorButton);

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
                //rub = response.body().rates.get("RUB");
                cur.setText(
                        new DecimalFormat("0.00").format(response.body().rates.get("RUB"))
                );
                rub = Double.parseDouble(cur.getText().toString());
                errorButton.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_message, Toast.LENGTH_SHORT).show();
                errorButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void repeatConnectOnClick(View view) {
        setupRetrofit();
    }

    public void eurToRubButtOnClick(View view) {
        if (rub == null || num.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_message_cur, Toast.LENGTH_SHORT).show();
            return;
        }

        res.setText(Double.toString(
                Double.parseDouble(num.getText().toString().
                        replace(",",".")) * rub
        ) + " RUB");
    }

    public void rubToEurButtonOnClick(View view) {
        if (rub == null || num.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_message_cur, Toast.LENGTH_SHORT).show();
            return;
        }

        res.setText(
                new DecimalFormat("0.000").format(
                Double.parseDouble(num.getText().toString().
                        replace(",",".")) / rub) + " EUR"
        );
    }
}
