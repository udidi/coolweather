package org.udidi.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.udidi.coolweather.db.City;
import org.udidi.coolweather.db.County;
import org.udidi.coolweather.db.Province;
import org.udidi.coolweather.gson.Weather;

import static android.content.ContentValues.TAG;

/**
 * Created by He.RO on 2017/3/21.
 */

public class Utility {
    private static final String TAG = "Utility";

    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                Log.d(TAG, "handleProvinceResponse: OK");
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "handleProvinceResponse: Wrong");
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                Log.d(TAG, "handleCityResponse: OK");
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "handleCityResponse: Wrong");
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if ((!TextUtils.isEmpty(response))) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countryObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countryObject.getString("name"));
                    county.setWeatherId(countryObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                Log.d(TAG, "handleCountyResponse: OK");
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "handleCountyResponse: Wrong");
        return false;
    }

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
