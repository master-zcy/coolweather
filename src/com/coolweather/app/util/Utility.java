package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.gson.HeWeatherBean;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.google.gson.Gson;

//实用
public class Utility {
	public static final String API_KEY = "8bdeda21439444f9acab316f2d781158";

	/**
	 * 解析和处理服务器返回的省级数据
	 */

	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {

			JSONArray allProvinces;
			try {
				allProvinces = new JSONArray(response);
				Log.e("zcy", "allProvinces.length" + allProvinces.length());

				if (allProvinces != null && allProvinces.length() > 0) {
					for (int i = 0; i < allProvinces.length(); i++) {
						JSONObject jsonObject = allProvinces.getJSONObject(i);
						Log.e("zcy", "name " + jsonObject.getString("name"));
						Province province = new Province();
						province.setId(jsonObject.getInt("id"));
						province.setProvinceName(jsonObject.getString("name"));
						// 将解析出来的数据存储到Province表
						coolWeatherDB.saveProvince(province);
					}
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * 解析和处理服务器返回的市级数据
	 */

	public synchronized static boolean handleCitiesResponse(
			CoolWeatherDB coolWeatherDB, String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			JSONArray allCities;
			try {
				allCities = new JSONArray(response);
				Log.e("zcy","allCities.length"+allCities.length());
				
				if (allCities != null && allCities.length() > 0) {
					for (int i = 0; i < allCities.length(); i++) {
						JSONObject jsonObject = allCities.getJSONObject(i);
						Log.e("zcy","name "+jsonObject.getString("name"));
						City city = new City();
						city.setId(jsonObject.getInt("id"));
						city.setCityName(jsonObject.getString("name"));
						city.setProvinceId(provinceId);
						// 将解析出来的数据存储到City表
						coolWeatherDB.saveCity(city);
					}
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 解析和处理服务器返回的县级数据
	 */

	public synchronized static boolean handleCountiesResponse(
			CoolWeatherDB coolWeatherDB, String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			JSONArray allCounties;
			try {
				allCounties = new JSONArray(response);
				Log.e("zcy","allCities.length"+allCounties.length());
				
				if (allCounties != null && allCounties.length() > 0) {
					for (int i = 0; i < allCounties.length(); i++) {
						JSONObject jsonObject = allCounties.getJSONObject(i);
						//Log.e("zcy","name "+jsonObject.getString("name"));
						County county = new County();
						county.setId(jsonObject.getInt("id"));
						county.setCountyName(jsonObject.getString("name"));
						county.setCountyCode(jsonObject.getString("weather_id"));
						county.setCityId(cityId);
						// 将解析出来的数据存储到County表
						coolWeatherDB.saveCounty(county);
					}
					return true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
	
	/*
	 * 解析服务器返回的JSON数据 并保存到本地
	 */
	public static void handleWeatherResponse(Context context,String response){
		try {
			JSONObject jsonObject=new JSONObject(response);
			JSONArray HeWeatherArray=jsonObject.getJSONArray("HeWeather");
			JSONObject  bean=HeWeatherArray.getJSONObject(0);
			Log.e("zcy", "天气 json字符串："+bean.toString());
			
			JSONObject bean_basic=bean.getJSONObject("basic");
			JSONObject bean_basic_update=bean.getJSONObject("update");	
			
			JSONArray daily_array=bean.getJSONArray("daily_forecast");
			JSONObject daliy_bean=daily_array.getJSONObject(0);
			JSONObject daliy_bean_cond=daliy_bean.getJSONObject("cond");

			JSONObject dally_bean_tmp=daliy_bean.getJSONObject("tmp");
			
//			Gson gson=new Gson();
//			HeWeatherBean heWeatherBean=gson.fromJson(bean.toString(), HeWeatherBean.class);
			if(bean!=null){
			
				String cityName=bean_basic.getString("city")	;
				Log.e("zcy", "城市名： "+cityName);
				String weatherCode=bean_basic.getString("id");
				Log.e("zcy", "weatherCode： "+weatherCode);
				String max=dally_bean_tmp.getString("max");
				Log.e("zcy", "max： "+max);
				String min=dally_bean_tmp.getString("min");
				Log.e("zcy", "min： "+min);
				String weatherDesp=daliy_bean_cond.getString("txt_d");
				Log.e("zcy", "weatherDesp： "+weatherDesp);
				String publishTime=bean_basic_update.getString("loc");
				Log.e("zcy", "publishTime： "+publishTime);
				saveWeatherInfo(context, cityName, weatherCode, max, min, weatherDesp, publishTime);
				
			}else{
				Log.e("zcy", "城市名：为空 ");
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中
	 */
	public static void saveWeatherInfo(Context context,String cityName,String weatherCode
			,String max,String min,String weatherDesp,String publishTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日 ",Locale.CHINA);
		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		editor.putBoolean("city_selectd", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", max);
		editor.putString("temp2", min);
		editor.putString("publish_time", publishTime);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("current_data", sdf.format(new Date()));
		Log.e("zcy", "aaa publish_time："+publishTime);
		editor.commit();	
	}
}
