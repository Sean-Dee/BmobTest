package demo;

import java.util.List;

import bmob.bson.BSONObject;
import bmob.restapi.Bmob;

public class TestDemo {

	public static void main(String[] args) {

		initBmob();// 初始化

		insert();
		list();

	}

	// 使用RestAPI前必须先初始化，KEY可在Bmob应用信息里查询。
	private static void initBmob() {
		Bmob.initBmob("97a06b099c8aa5811e7e2058be30544b", "c10bb29cd8ce341fa83ba657ffd816bc");
		// 用到超级权限需要注册该Key
		// Bmob.initMaster("Your Bmob-Master-Key");
	}

	private static void list() {
		String result = Bmob.findAll("ArrivalTime");
		System.out.println(result);
	}

	private static void insert() {

		JDBCUtil jdbc = new JDBCUtil();
		List<BSONObject> bsonList = jdbc.findAll();

		for (BSONObject bsonObject : bsonList) {
			Bmob.insert("ArrivalTime", bsonObject.toString());
		}
	}

}