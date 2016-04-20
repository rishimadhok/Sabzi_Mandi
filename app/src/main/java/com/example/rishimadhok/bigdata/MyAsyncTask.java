package com.example.rishimadhok.bigdata;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Zeus on 4/16/2016.
 */
public class MyAsyncTask extends AsyncTask<String, Void,ArrayList<CommodityClass>> {
    ArrayList<CommodityClass> ret;
    MyAsyncTaskInterface listener;
    MyAsyncTask(){

    }
    @Override
    protected ArrayList<CommodityClass> doInBackground(String... params) {
        try {
            URL url = new URL("https://data.gov.in/api/datastore/resource.json?resource_id=9ef84268-d588-465a-a308-a864a43d0070&api-key=e336f8d712fcbebae7d350e5c9363063");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
//            CommodityClass.deleteAll(CommodityClass.class);
            Scanner s = new Scanner(inputStream);
            StringBuffer output = new StringBuffer();
            while (s.hasNext()) {
                output.append(s.nextLine());
            }
            Log.i("Limit1",String.valueOf(output.length()));
//            Log.i("jsondata1", output.toString());

            return parseJSON(output.toString());

        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private ArrayList<CommodityClass> parseJSON(String output){
        try {
            Log.i("Length",String.valueOf(output.length()));
            JSONObject obj = new JSONObject(output);
            JSONArray rr = obj.getJSONArray("records");
//            Stock[] stocks = new Stock[quotes.length()];
            ret=new ArrayList<>();
            for(int i = 0; i < rr.length(); i++){
                JSONObject cur = rr.getJSONObject(i);
                CommodityClass temp=new CommodityClass();


                temp.modal_price=cur.getInt("modal_price");
                temp.modal_price/=100;
                temp.commodity=cur.getString("commodity");
                temp.state=cur.getString("state");
                Log.i("states",temp.state);
//                Log.i("Modal",String.valueOf(temp.modal_price));
                temp.market=cur.getString("market");
                temp.variety=cur.getString("variety");
                ret.add(temp);
            }
            return ret;

        } catch (JSONException e) {
            return null;
        }
    }

    public void setMyAsyncTaskListener(MyAsyncTaskInterface listener){
        this.listener=listener;
    }

    @Override
    protected void onPostExecute(ArrayList<CommodityClass> commodityClasses) {
        if(listener!=null){
            listener.onTaskComplete(commodityClasses);
        }
    }

    public interface MyAsyncTaskInterface{
        void onTaskComplete(ArrayList<CommodityClass> commodityClasses);
    }

}
