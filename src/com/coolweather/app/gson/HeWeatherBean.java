package com.coolweather.app.gson;

import java.util.List;

import com.coolweather.app.gson.Daily_forecast.Daily_forecastItem;

public class HeWeatherBean {

//	public List<HeWeatherBean> HeWeather;
	
	public Basic basic;
	
	public String status;
	
	public List<Daily_forecastItem> daily_forecast;
	
	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Daily_forecastItem> getDaily_forecast() {
		return daily_forecast;
	}

	public void setDaily_forecast(List<Daily_forecastItem> daily_forecast) {
		this.daily_forecast = daily_forecast;
	}


	
	
}
