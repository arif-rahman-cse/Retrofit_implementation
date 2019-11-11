package com.smarifrahman.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smarifrahman.Interface.ApiInterface;
import com.smarifrahman.R;
import com.smarifrahman.model.UserInfo;
import com.smarifrahman.retrofit.RetrofitApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    TextView responseTextView;
    ProgressBar prograssbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTextView = findViewById(R.id.text);
        prograssbar = findViewById(R.id.progress_bar_id);
        button = findViewById(R.id.button_id);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_id) {
            showMyIp();

            Log.d(TAG, "GET button clicked");
        }
    }

    private void showMyIp() {

        prograssbar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<UserInfo> call = apiInterface.getMyIp();

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                Log.d(TAG, "onResponse: onResponse called");

                prograssbar.setVisibility(View.GONE); //network call success. So hide progress bar

                UserInfo userInfo = response.body();

                if (response.code() == 200 && userInfo != null) { //response code 200 means server call successful
                    //data found. So place the data into TextView
                    Log.d(TAG, "onResponse: "+response.code());
                    responseTextView.setText(userInfo.getIp());
                    //cityTextView.setText(serverResponse.getCity());
                    //countryTextView.setText(serverResponse.getCountry());
                } else {
                    //somehow data not found. So error message showing in first TextView
                    responseTextView.setText(response.message());
                    //cityTextView.setText("");
                    //countryTextView.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: onFailure called");

                prograssbar.setVisibility(View.GONE); //network call failed. So hide progress bar

                //network call failed due to disconnect internet connection or server error
                responseTextView.setText(t.getMessage());
                //cityTextView.setText("");
                //countryTextView.setText("");
            }
        });

    }
}
