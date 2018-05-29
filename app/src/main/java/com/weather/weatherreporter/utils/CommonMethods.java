package com.weather.weatherreporter.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weather.weatherreporter.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * all common methods written here
 * can access from all other activity and adapters
 */

public class CommonMethods {
    private static CommonMethods commonMethods;

    public static CommonMethods getCommonMethods(){
        if(commonMethods==null){
            commonMethods = new CommonMethods();
        }
        return commonMethods;
    }

    /**
     * create progress dialog and display
     * @param activity
     * @param msg
     * @return
     */
    public ProgressDialog showProgress(Activity activity,String msg){
        ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();

        return progressDialog;
    }

    /**
     * dismiss the progress dialog
     * @param progressDialog
     */
    public void closeProgrss(ProgressDialog progressDialog){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    /**
     * load image into imageview from anywhere in this app
     * using glide library
     * place holder is R.drawable.cloudicon
     * glide library wll cache all the image automatically
     * @param context
     * @param url
     * @param imageView
     */

    public void loadImage(Context context, String url, ImageView imageView){
        try{
            Glide.with(context).load(url)
                    .crossFade()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.cloudicon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }catch (Exception e){

        }
    }

    /**
     * format the date to display
     * @param strDate
     * @return
     */
    public String getDate(String  strDate){
        String thisDate = null;
        SimpleDateFormat currentFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat newFmt     = new SimpleDateFormat("dd-MMM-yyyy, hh:mm");
        try {
            Date currentFormat = currentFmt.parse(strDate);
           thisDate = newFmt.format(currentFormat);
        }catch (Exception e){
        }
        return thisDate;
    }
}
