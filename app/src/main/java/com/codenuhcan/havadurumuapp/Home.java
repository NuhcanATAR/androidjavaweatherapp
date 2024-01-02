package com.codenuhcan.havadurumuapp;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class Home extends AppCompatActivity {

    private TextView temperatureTextView;
    private TextView cityValueTextView;

    private TextView textView11View;

    private TableLayout forecastTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        temperatureTextView = findViewById(R.id.txtSicaklik);
        cityValueTextView = findViewById(R.id.txtUlke2);
        textView11View = findViewById(R.id.textView11);
        forecastTableLayout = findViewById(R.id.forecastTableLayout);


        String cityValue = "corum";

        // api url
        String apiUrl = "https://api.collectapi.com/weather/getWeather?data.lang=tr&data.city="+cityValue;

        // http isteği oluştur
        new HttpRequestTask().execute(apiUrl);
    }

    private void setCityValue(String cityValue){
        cityValueTextView.setText(cityValue);
    }

    private void populateForecastTable(JSONArray forecastData) throws JSONException {
        for (int i = 0; i < forecastData.length(); i++) {
            JSONObject dayData = forecastData.getJSONObject(i);
            String day = dayData.getString("date");
            String status = dayData.getString("status");
            String degree = dayData.getString("degree");

            switch (status) {
                case "Rain":
                    status = "Yağmurlu";
                    break;
                case "Clouds":
                    status = "Bulutlu";
                    break;
                case "Clear":
                    status = "Açık Hava";
                    break;
                case "Rain and Snow":
                    status = "Karla Karışık Yağmur";
                    break;
                default:
                    status = "Yeni Hava Durumu Geldi!!";
                    break;
            }

            // her satır arasına boşluk verme
            View spacer = new View(this);
            TableRow.LayoutParams spacerParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 10);
            spacer.setLayoutParams(spacerParams);

            // yeni satır
            TableRow row = new TableRow(this);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(rowParams);

            // TextView'ler
            TextView dayTextView = new TextView(this);
            dayTextView.setText(day);
            dayTextView.setGravity(Gravity.START);
            dayTextView.setTextColor(Color.WHITE);
            dayTextView.setTextSize(17);
            dayTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            TextView statusTextView = new TextView(this);
            statusTextView.setText(status);
            statusTextView.setGravity(Gravity.CENTER);
            statusTextView.setTextColor(Color.WHITE);
            statusTextView.setTextSize(17);
            statusTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            TextView degreeTextView = new TextView(this);
            degreeTextView.setText(degree + "°C");
            degreeTextView.setGravity(Gravity.END);
            degreeTextView.setTextColor(Color.WHITE);
            degreeTextView.setTextSize(17);
            degreeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            // tabloya textviewleri ekleme
            row.addView(dayTextView);
            row.addView(statusTextView);
            row.addView(degreeTextView);

            // table layoute yeni satır ve boşluk ekleme
            forecastTableLayout.addView(spacer);
            forecastTableLayout.addView(row);
        }
    }





    private void setWeatherStatusValue(String weatherValue){
        textView11View.setText(weatherValue);
    }

    private void setTemperature(String temperature) {
        // hava sıcaklığını ekleme
        temperatureTextView.setText(temperature + "°C");
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String apiUrl = params[0];
            String responseString = "";

            try {
                // http bağlantısı
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // istek bağlantısı düzenle
                connection.setRequestMethod("GET");
                connection.setRequestProperty("content-type", "application/json");
                connection.setRequestProperty("authorization", "apikey KEY");

                // bağlantı başlatma
                connection.connect();

                // api den yanıt alma
                InputStream inputStream = connection.getInputStream();
                responseString = convertStreamToString(inputStream);

                // bağlantı sonlandır
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String response) {
            try {

                JSONObject jsonResponse = new JSONObject(response);
                String temperature = jsonResponse.getJSONArray("result").getJSONObject(0).getString("degree");
                String weatherStatus = jsonResponse.getJSONArray("result").getJSONObject(0).getString("status");
                JSONArray forecast = jsonResponse.getJSONArray("result");
                switch (weatherStatus) {
                    case "Rain":
                        weatherStatus = "Yağmurlu";
                        break;
                    case "Clear":
                        weatherStatus = "Açık";
                        break;
                    case "Clouds":
                        weatherStatus = "Bulutlu";
                        break;
                    case "Rain and Snow":
                        weatherStatus = "Karla Karışık Yağmur";
                        break;
                    default:
                        weatherStatus = "Yeni Hava Durumu Geldi!!";
                        break;
                }
                String cityValue = "Çorum";
                setCityValue(cityValue);
                populateForecastTable(forecast);
                setWeatherStatusValue(weatherStatus);
                setTemperature(temperature);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String convertStreamToString(InputStream inputStream) {
            java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }
}

