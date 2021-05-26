package com.watermark.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCmd {
	public static void main(String[] args) {
		try {
			Process pro = Runtime.getRuntime().exec("cmd /c cd Pythonsource&&python TextIn.py 1.png text");
			Runtime.getRuntime().exec("cmd /c cd Pythonsource&&python TextOut.py ok1.png");
			// /calc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
			String msg = null;
			System.out.println("em.。。");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
		} catch (IOException exception) {
			System.out.println(exception);
		}
	}
}
