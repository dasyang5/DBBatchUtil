package com.alex.tool.db.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author 
 *
 */
public class UUIDUtil {

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID32(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtil.getUUID());
		System.out.println(UUIDUtil.getUUID32());
	}
}
