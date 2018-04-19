package com.coolweather.app.gson;

public class Daily_forecast {
	
	public class Daily_forecastItem{
		public String data;//日期
		public Cound cound;
		public Tmp tmp;
		
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public Cound getCound() {
			return cound;
		}
		public void setCound(Cound cound) {
			this.cound = cound;
		}
		public Tmp getTmp() {
			return tmp;
		}
		public void setTmp(Tmp tmp) {
			this.tmp = tmp;
		}
	
		
		public class Cound{
			public String txt_d;

			public String getTxt_d() {
				return txt_d;
			}

			public void setTxt_d(String txt_d) {
				this.txt_d = txt_d;
			}
	
		}
		public class Tmp{
			public String getMax() {
				return max;
			}
			public void setMax(String max) {
				this.max = max;
			}
			public String getMin() {
				return min;
			}
			public void setMin(String min) {
				this.min = min;
			}
			public String max;//最高温度
			public String min;//最小温度
		}
	}

}
