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
import com.weather.weatherreporter.models.CityModel;
import com.weather.weatherreporter.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * display all cities name in listview
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    Activity mContext;
    List<CityModel> thisList = new ArrayList<CityModel>();

    public CityAdapter(Activity context, List<CityModel> list){
        this.mContext   =    context;
        this.thisList   =    list;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {

        try {

            if(!thisList.get(position).getCity_name().equalsIgnoreCase("null")&&!TextUtils.isEmpty(thisList.get(position).getCity_name())){
                holder.tv_city.setText(thisList.get(position).getCity_name());
            }else {
                holder.cv_city.setVisibility(View.GONE);
            }

            /**
             * go to next activity for display weather details
             * pass the waether json object through intent
             */
            holder.cv_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent weather  = new Intent(mContext,WeatherActivity.class);
                    weather.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    weather.putExtra("Weather" , thisList.get(position).getWeather());
                    mContext.startActivity(weather);
                }
            });

        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return thisList.size();
    }

    /**
     * define the viewholder and initialize views
     */
    public class CityViewHolder extends RecyclerView.ViewHolder {

        TextView tv_city;
        CardView cv_city;
        
        private CityViewHolder(View view){
            super(view);

            tv_city  =   (TextView)    view.findViewById(R.id.tv_city_name);
            cv_city  =   (CardView)    view.findViewById(R.id.card_city);

        }
    }
}