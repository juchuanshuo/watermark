package com.watermark.controller;

import java.io.InputStream;
import java.util.Properties;

public class Dir {
	public static String path = null;
	private static final String PROPERTIES_NAME = "path.properties";
	public static String cmd = null;
	
	static {
		Properties prop = new Properties();
        try {
            ClassLoader classLoader = Dir.class.getClassLoader();// 读取属性文件xxxxx.properties
            InputStream in = classLoader.getResourceAsStream("path.properties");
            prop.load(in); /// 加载属性列表
            path = prop.getProperty("path");
            cmd = prop.getProperty("cmd");
            in.close();
        } catch (Exception e) {
            
        }
	}
	private static void showConfig() {
		System.out.println("-----------------------配置信息-----------------");
		System.out.println("dirver: " + path);
		System.out.println("----------------------------------------------");
	}

}
