package com.coolweather.app.activity;

import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;
import com.example.coolweather.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements
		android.view.View.OnClickListener {

	private LinearLayout weatherInfoLayout;
	/**
	 * 用于显示城市名
	 */
	private TextView cityNameText;
	/**
	 * 用于显示发布时间
	 */
	private TextView publishText;

	private TextView weatherDespText;

	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDataText;

	// 切换城市按钮
	private Button switchCity;
	// 更新天气按钮
	private Button refreshWeather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);

		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDataText = (TextView) findViewById(R.id.current_data);
		switchCity = (Button) findViewById(R.id.swith_city);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);

		String countyCode = getIntent().getStringExtra("county_code");
		if (!TextUtils.isEmpty(countyCode)) {
			// 有县级代号时就去查询天气
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			quertyWeatherCode(countyCode);
		} else {
			// 没有县级代号就直接显示本地
			showWeaher();
		}

		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
	}

	/**
	 * 查询县级代号所对应的天气代号
	 * 
	 * @param countyCode
	 */
	private void quertyWeatherCode(String countyCode) {
		// TODO Auto-generated method stub
		String address = "http://guolin.tech/api/weather?cityid="+countyCode+"&key="
				+Utility.API_KEY;
		queryFromServer(address, countyCode);
	}

	/**
	 * 根据传入的地址和类型向服务器查询天气
	 * 
	 * @param address
	 * @param countyCode
	 */
	private void queryFromServer(String address, String countyCode) {
		// TODO Auto-generated method stub
		HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {

					@Override
					public void onFinish(String response) {
						// TODO Auto-generated method stub
						Log.e("zcy", "Weather  get onFinish1 ：");
						Utility.handleWeatherResponse(WeatherActivity.this,
								response);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.e("zcy", "Weather  get onFinish2 ：");
								showWeaher();
							}
						});
					}

					@Override
					public void onError(Exception e) {
						// TODO Auto-generated method stub
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.e("zcy", "Weather  get onError ：");
								publishText.setText("同步失败");
							}
						});
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.swith_city:
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("form_weather_activity", true);
			startActivity(intent);
			finish();
			break;

		case R.id.refresh_weather:
			publishText.setText("同步中...");
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode=prefs.getString("weather_code", "");
			if(!TextUtils.isEmpty(weatherCode)){
				quertyWeatherCode(weatherCode);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上
	 */
	private void showWeaher() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Log.e("zcy"," showWeaher(): "+ prefs.getString("weather_desp", ""));
		
		cityNameText.setText(prefs.getString("city_name", ""));
		temp2Text.setText(prefs.getString("temp1", "")+"°C");
		temp1Text.setText(prefs.getString("temp2", "")+"°C");
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("" + prefs.getString("publish_time", "") + "发布");
		currentDataText.setText(prefs.getString("current_data", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}
}
