package com.weather.weatherreporter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weather.weatherreporter.R;
import com.weather.weatherreporter.adapters.CityAdapter;
import com.weather.weatherreporter.adapters.WeatherAdapter;
import com.weather.weatherreporter.interfaces.WeatherInterrface;
import com.weather.weatherreporter.models.WeatherModel;
import com.weather.weatherreporter.utils.AppLog;
import com.weather.weatherreporter.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * display data for 5 days
 * by default display max, min,and current weather of current day
 * load other days in recycler view. Click on recycler view , display all data in main view
 * display maximum 5 days weather data include current day
 */

public class WeatherActivity  extends AppCompatActivity implements WeatherInterrface{

    String strweather =null;
    ImageView img_icon;
    RecyclerView recyclerWeather;
    TextView tv_max,tv_min,tv_temp,tv_summary,tv_when, tv_humidty;

    WeatherAdapter weatherAdapter;
    List<WeatherModel> weatherList = new ArrayList<WeatherModel>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        /**
         * set home button and title
         * set back icon for tool bar
         */

        Toolbar toolbar    = (Toolbar)findViewById(R.id.toolbar_weather);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.city_list));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);

        TextView tv_wthr = (TextView) findViewById(R.id.tv_toolbar_heading);
        tv_wthr.setText(getResources().getString(R.string.weather));

        img_icon        =   (ImageView) findViewById(R.id.img_weather_icon);
        tv_max          =   (TextView)  findViewById(R.id.tv_weather_temp_max);
        tv_min          =   (TextView)  findViewById(R.id.tv_weather_temp_min);
        tv_temp         =   (TextView)  findViewById(R.id.tv_weather_temp);
        tv_summary      =   (TextView)  findViewById(R.id.tv_weather_summary);
        tv_when         =   (TextView)  findViewById(R.id.tv_weather_head);
        tv_humidty      =   (TextView)  findViewById(R.id.tv_weather_humidity);
        recyclerWeather =   (RecyclerView) findViewById(R.id.recyler_weather);

        /**
         * set horizontal linear layout manger for recycler. so items will display in horizontal manner
         */
        recyclerWeather.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerWeather.setLayoutManager(layoutManager);

        /**
         * receive intent data from city activity
         */
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            strweather = bundle.getString("Weather");
        }


        /**
         * convert the intent value into json object and read values from 'currenty' and 'daily json obj
         * load data into adapter then set adapter to recycler
         */

        try {
            JSONObject obj = new JSONObject(strweather);

            JSONObject json = obj.getJSONObject("currently");
            WeatherModel weather = new WeatherModel();
            weather.setWhen(json.getString("timeString"));
            weather.settMax(json.getString("tempMaxCelcius"));
            weather.settMax(json.getString("tempMinCelcius"));
            weather.settNow(json.getString("tempCelsius"));
            weather.settSummary(json.getString("summary"));
            weather.setIconUrl(json.getString("iconUrl"));
            weather.setHumidty(json.getString("humidity"));
            weather.setLatitude(json.getString("latitude"));
            weather.setLongitude(json.getString("longitude"));

            /**
             * display this by default
             */
            thisDayWeather(weather,0);

            weatherList.add(weather);

            JSONObject json1 = obj.getJSONObject("daily");

            /**
             * read max 5 days weather data
             */
            Iterator<String> iter = json1.keys();
            int s =0;
            while (s<5) {
                String key = iter.next();
                try {
                    Object value = json1.get(key);
                    JSONObject jsonObject =  new JSONObject(value.toString());

                    WeatherModel weather1 = new WeatherModel();
                    weather1.setWhen(jsonObject.getString("timeString"));
                    weather1.settMax(jsonObject.getString("tempMaxCelcius"));
                    weather1.settMin(jsonObject.getString("tempMinCelcius"));
                    weather1.settNow(jsonObject.getString("tempCelsius"));
                    weather1.settSummary(jsonObject.getString("summary"));
                    weather1.setIconUrl(jsonObject.getString("iconUrl"));
                    weather1.setHumidty(jsonObject.getString("humidity"));
                    weather1.setLatitude(jsonObject.getString("latitude"));
                    weather1.setLongitude(jsonObject.getString("longitude"));

                    weatherList.add(weather1);
                    s++;

                } catch (JSONException e) {
                }
            }

        }catch (Exception e){
            AppLog.d("====WWWWW " +e);
        }

        Collections.sort(weatherList, new Comparator<WeatherModel>(){
            public int compare(WeatherModel w1, WeatherModel w2) {
                return w1.getWhen().compareToIgnoreCase(w2.getWhen());
            }
        });

        weatherAdapter   =   new WeatherAdapter(WeatherActivity.this, weatherList,this);
        recyclerWeather.setAdapter(weatherAdapter);
    }

    /**
     * handle back press on tool bar back button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * interface used to display the data for particular date.
     * if user click on recycler view then data will pass to this method from adapter and display in main view
     * @param weatherModel
     * @param pos
     */
    @Override
    public void thisDayWeather(final WeatherModel weatherModel, final int pos) {
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if(pos==0) {
                                  tv_when.setText("Currently");
                              }else {
                                  tv_when.setText(CommonMethods.getCommonMethods().getDate(weatherModel.getWhen()));
                              }
                              if(TextUtils.isEmpty(weatherModel.gettMax())){
                                  tv_max.setText("-");
                              }else {
                                  tv_max.setText(weatherModel.gettMax() + " \u2103");
                              }
                              if(TextUtils.isEmpty(weatherModel.gettMin())){
                                  tv_min.setText("-");
                              }else {
                                  tv_min.setText(weatherModel.gettMin() + " \u2103");
                              }
                              if(TextUtils.isEmpty(weatherModel.gettNow())){
                                  tv_temp.setText("-");
                              }else {
                                  tv_temp.setText(weatherModel.gettNow()  +  " \u2103");
                              }

                              tv_humidty.setText(weatherModel.getHumidty());
                              tv_summary.setText(weatherModel.gettSummary());
                              CommonMethods.getCommonMethods().loadImage(WeatherActivity.this,weatherModel.getIconUrl(),img_icon);
                          }
                      });
        
    }

}
