package com.coolweather.app.gson;

public class Basic {

	/*
	 *  "id":"CN101100101",
	 *   "city":"̫ԭ",
	 *   
	 */
	public String city;//������
	public String id;//����id
	public Update update;//����ʱ��
	
	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	public class Update{
		/*
		 * "loc":"2018-04-19 10:47",
		 */
		public String loc;//�����ĸ���ʱ��

		public String getLoc() {
			return loc;
		}

		public void setLoc(String loc) {
			this.loc = loc;
		}
	}
}
