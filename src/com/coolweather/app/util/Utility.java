package com.coolweather.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

//ʵ��
public class Utility {
	public static final String API_KEY = "8bdeda21439444f9acab316f2d781158";

	/**
	 * �����ʹ�����������ص�ʡ������
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
						// ���������������ݴ洢��Province��
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
	 * �����ʹ�����������ص��м�����
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
						// ���������������ݴ洢��City��
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
	 * �����ʹ�����������ص��ؼ�����
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
						Log.e("zcy","name "+jsonObject.getString("name"));
						County county = new County();
						county.setId(jsonObject.getInt("id"));
						county.setCountyName(jsonObject.getString("name"));
						county.setCityId(cityId);
						// ���������������ݴ洢��County��
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
}
