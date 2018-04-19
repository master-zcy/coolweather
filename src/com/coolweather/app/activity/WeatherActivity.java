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
	 * ������ʾ������
	 */
	private TextView cityNameText;
	/**
	 * ������ʾ����ʱ��
	 */
	private TextView publishText;

	private TextView weatherDespText;

	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDataText;

	// �л����а�ť
	private Button switchCity;
	// ����������ť
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
			// ���ؼ�����ʱ��ȥ��ѯ����
			publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			quertyWeatherCode(countyCode);
		} else {
			// û���ؼ����ž�ֱ����ʾ����
			showWeaher();
		}

		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
	}

	/**
	 * ��ѯ�ؼ���������Ӧ����������
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
	 * ���ݴ���ĵ�ַ���������������ѯ����
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
						Log.e("zcy", "Weather  get onFinish1 ��");
						Utility.handleWeatherResponse(WeatherActivity.this,
								response);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.e("zcy", "Weather  get onFinish2 ��");
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
								Log.e("zcy", "Weather  get onError ��");
								publishText.setText("ͬ��ʧ��");
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
			publishText.setText("ͬ����...");
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
	 * ��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ��������
	 */
	private void showWeaher() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Log.e("zcy"," showWeaher(): "+ prefs.getString("weather_desp", ""));
		
		cityNameText.setText(prefs.getString("city_name", ""));
		temp2Text.setText(prefs.getString("temp1", "")+"��C");
		temp1Text.setText(prefs.getString("temp2", "")+"��C");
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("" + prefs.getString("publish_time", "") + "����");
		currentDataText.setText(prefs.getString("current_data", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}
}
