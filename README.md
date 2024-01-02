# Hava Durumu Uygulaması
Android java ile Çorum ilinin Günlük ve 6 günlük hava durumu gösteren mobil uygulama projesi

<h3>build.gradle Depencines ve packagingOptions bölümü:</h3>

```java

packagingOptions {
  exclude 'META-INF/DEPENDENCIES'
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.android.support.constraint:constraint-layout:2.0.4'
    implementation 'com.android.volley:volley:1.2.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}



```

<h3>AndroidX için gradle.properties içine</h3>

```java
android.useAndroidX=true
android.enableJetifier=true
```
<h3>HTTP Talepleri İçin Kullanılan Kütüphaneler</h3>

```java
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
```

<h3>Ekranda bir süre bekledikten sonra otomatik yönlendirme</h3>

```java
new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
                finish(); // Bu sayfayı kapat
            }
        }, 4000); // 4 saniye beklet
```

<h3>Api URL Hazırlama</h3>

```java
  String cityValue = "corum";
  
  // api url
  String apiUrl = "https://api.collectapi.com/weather/getWeather?data.lang=tr&data.city="+cityValue;
```

<h3>JSON API Yanıtını işleyen hedef bilgileri alma</h3>

```java
  // json metni olan responseyi jsonobject nesnesine çevirme
  JSONObject jsonResponse = new JSONObject(response);
  // hava sıcaklığını alma
  String temperature = jsonResponse.getJSONArray("result").getJSONObject(0).getString("degree");
  // hava durumunu alma
  String weatherStatus = jsonResponse.getJSONArray("result").getJSONObject(0).getString("status");
  // hava durumu tahminleri alma
  JSONArray forecast = jsonResponse.getJSONArray("result");

```


<h3>Http Bağlantısı Hazırlama</h3>

```java
URL url = new URL(apiUrl);
HttpURLConnection connection = (HttpURLConnection) url.openConnection();

```


<h3>İstek Bağlanıtısı Hazırlama</h3>

```java
connection.setRequestMethod("GET");
connection.setRequestProperty("content-type", "application/json");
connection.setRequestProperty("authorization", "apikey KEY");

```



<h3>Bağlantı Başlatma</h3>

```java
connection.connect();

```

<h3>Api den Yanıt Alma</h3>

```java
InputStream inputStream = connection.getInputStream();
 responseString = convertStreamToString(inputStream);

```

<h3>Bağlantı Sonlandırma</h3>

```java
connection.disconnect();

```


<h3>Uygulama Ekran Görüntüleri</h3>
<table>
  <tr>
     <td>
      <img src="https://github.com/NuhcanATAR/androidjavaweatherapp/assets/77950761/522d6bd3-3424-4451-a435-f71287eb50a4" width="200" height="400"/>
    </td>
    <td>
      <img src="https://github.com/NuhcanATAR/androidjavaweatherapp/assets/77950761/b86961ec-5253-4bd4-aee8-01ea82315111" width="200" height="400"/>
    </td>
    
  </tr>
</table>

