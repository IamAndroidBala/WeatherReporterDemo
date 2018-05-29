package com.weather.weatherreporter.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.weatherreporter.R;
import com.weather.weatherreporter.activity.WeatherActivity;
import com.weather.weatherreporter.interfaces.WeatherInterrface;
import com.weather.weatherreporter.models.CityModel;
import com.weather.weatherreporter.models.WeatherModel;
import com.weather.weatherreporter.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * display the day's weather data
 * if user click on recyler item , pass current  postion item to interface to display
 *  max 5 days weather should be display
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CityViewHolder> {

    Activity mContext;
    List<WeatherModel> thisList = new ArrayList<WeatherModel>();
    private WeatherInterrface thisWeatherInterrface;

    public WeatherAdapter(Activity context, List<WeatherModel> list,WeatherInterrface weatherInterrface){
        this.mContext           =    context;
        this.thisList           =    list;
        thisWeatherInterrface   =   weatherInterrface;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {

        try {

            if(position==0){
                holder.tv_when.setText(mContext.getResources().getString(R.string.currently));
            }else {
                String[] when = thisList.get(position).getWhen().split("\\s+");
                if (when.length == 2) {
                    holder.tv_when.setText(when[0]);
                } else {
                    holder.tv_when.setText(thisList.get(position).getWhen());
                }
            }

            if(thisList.get(position).gettMax()==null || TextUtils.isEmpty(thisList.get(position).gettMax())){
                holder.tv_max.setText(" - " );
            }else {
                holder.tv_max.setText( thisList.get(position).gettMax() + "\u2103");
            }
            if(thisList.get(position).gettMin()==null || TextUtils.isEmpty(thisList.get(position).gettMin())){
                holder.tv_min.setText(" - ");
            }else {
                holder.tv_min.setText(thisList.get(position).gettMin() + "\u2103");
            }

            if(thisList.get(position).gettSummary()==null || TextUtils.isEmpty(thisList.get(position).gettSummary())){
                holder.tv_hmdy.setText(" - ");
            }else {
                holder.tv_hmdy.setText(thisList.get(position).gettSummary());
            }

            holder.cv_weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thisWeatherInterrface.thisDayWeather(thisList.get(position),position);
                }
            });

        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return thisList.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        TextView tv_when,tv_max,tv_min,tv_hmdy;
        CardView cv_weather;

        private CityViewHolder(View view){
            super(view);

            tv_when     =   (TextView)    view.findViewById(R.id.tv_weather_when);
            tv_max      =   (TextView)    view.findViewById(R.id.tv_weather_max);
            tv_min      =   (TextView)    view.findViewById(R.id.tv_weather_min);
            tv_hmdy     =   (TextView)    view.findViewById(R.id.tv_weather_humidity);
            cv_weather  =   (CardView)    view.findViewById(R.id.card_weather);

        }
    }
}