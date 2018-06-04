package mapsoft.com.myretrofit;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen_Kwok on 2016/6/12.
 * 一条线路上的车辆信息
 */
public class VehicleInfo extends Object{
	private String name;            // 下一站距离百分比
	private int count;              // 站数
	private float distance;         // 最近车辆位置
	private List<Map<String,Object>> vehicle;    // 车辆位置

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private int num;                //车上人数

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String,Object>> getVehicle() {
		return vehicle;
	}

	public void setVehicle(List<Map<String,Object>> vehicle) {
		this.vehicle = vehicle;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}
