package com.watermark.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCmd {
	public static void main(String[] args) {
		try {
			Process pro = Runtime.getRuntime().exec("cmd /c cd Pythonsource&&python TextIn.py 1.png text");
			Runtime.getRuntime().exec("cmd /c cd Pythonsource&&python TextOut.py ok1.png");
			// /calc"��calc����Ҫִ�д򿪼�������������ùػ����Լ�����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // ��Ȼcmd�������ֱ�����������ͨ��IO���������Ա�֤�����ݽ���һ�����塣
			String msg = null;
			System.out.println("em.����");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
		} catch (IOException exception) {
			System.out.println(exception);
		}
	}
}
