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
            ClassLoader classLoader = Dir.class.getClassLoader();// ��ȡ�����ļ�xxxxx.properties
            InputStream in = classLoader.getResourceAsStream("path.properties");
            prop.load(in); /// ���������б�
            path = prop.getProperty("path");
            cmd = prop.getProperty("cmd");
            in.close();
        } catch (Exception e) {
            
        }
	}
	private static void showConfig() {
		System.out.println("-----------------------������Ϣ-----------------");
		System.out.println("dirver: " + path);
		System.out.println("----------------------------------------------");
	}

}
