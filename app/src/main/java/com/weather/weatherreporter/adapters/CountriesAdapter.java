package com.weather.weatherreporter.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weather.weatherreporter.activity.CityListActivity;
import com.weather.weatherreporter.R;
import com.weather.weatherreporter.models.CountryModel;
import com.weather.weatherreporter.utils.AppLog;
import com.weather.weatherreporter.utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * display all country name
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder> {

    Activity mContext;
    List<CountryModel> thisList = new ArrayList<CountryModel>();

    public CountriesAdapter(Activity context, List<CountryModel> list){
        this.mContext   =    context;
        this.thisList   =    list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {

            holder.tv_country.setText(thisList.get(position).getCountry_name());

            CommonMethods.getCommonMethods().loadImage(mContext,thisList.get(position).getCountry_flag(),holder.img_flag);

            /**
             * go to city list activity
             * pass the city list paramater through intent
             */
            holder.cv_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cityList = new Intent(mContext,CityListActivity.class);
                    cityList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cityList.putExtra("Cities",thisList.get(position).getCountry_cities());
                    mContext.startActivity(cityList);

                }
            });

        }catch (Exception e){
            AppLog.d("Country Adapter ex " + e);
        }
    }

    @Override
    public int getItemCount() {
        return thisList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_country;
        ImageView img_flag;
        CardView cv_country;

        private MyViewHolder(View view){
            super(view);

            tv_country  =   (TextView)    view.findViewById(R.id.tv_country_name);
            cv_country  =   (CardView)    view.findViewById(R.id.card_country);
            img_flag    =   (ImageView)   view.findViewById(R.id.img_country_flag);

        }
    }
}