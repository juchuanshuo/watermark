package com.watermark.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主控制器
 */
@Controller

public class MainController {

	@ResponseBody
	@RequestMapping(value = "/TextIn")
	public Map TextIn(String image, String text) {
		Map a = new HashMap<>();
		try {
			Process pro = Runtime.getRuntime()
					.exec(Dir.cmd+" "+Dir.path+"&&python TextIn.py " + image + " " + text);
			// /calc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
			String msg = null;
			System.out.println("em.。。");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "嵌入成功");
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "嵌入失败，请重试");
		}

		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/TextOut")
	public Map TextOut(MultipartFile file, HttpServletRequest request) {
		// 保存数据库的路径
		// 定义文件保存的本地路径
		String localPath = Dir.path+"/";
		// 定义 文件名
		String filename = new Date().getTime() + "";
		// 获得文件类型（可以判断如果不是图片，禁止上传）
		String contentType = file.getContentType();
		// 获得文件后缀名
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// 得到 文件名
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// 文件保存路径
		Map a = new HashMap<>();
		StringBuilder result = new StringBuilder();
		try {
			file.transferTo(new File(localPath + filename));
			Process pro = Runtime.getRuntime().exec(Dir.cmd+" "+Dir.path+"&&python TextOut.py " + filename);
			try {
				Thread.sleep(1000); // 1000 毫秒，也就是1秒.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			File file2 = new File(""+Dir.path+"/" + filename + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file2));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (IOException exception) {
			System.out.println(exception);
		}
		a.put("text", result.toString().substring(2));
		return a;
	}

	@RequestMapping("/upload")
	@ResponseBody
	public String test(MultipartFile file, HttpServletRequest request) throws IOException {
		// 保存数据库的路径
		// 定义文件保存的本地路径
		String localPath = Dir.path+"/";
		// 定义 文件名
		String filename = new Date().getTime() + "";
		// 获得文件类型（可以判断如果不是图片，禁止上传）
		String contentType = file.getContentType();
		// 获得文件后缀名
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// 得到 文件名
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// 文件保存路径
		file.transferTo(new File(localPath + filename));
		return filename;
		/*
		 * System.out.println("comming!"); String path = ""+Dir.path+"";
		 * System.out.println("path>>" + path); String fileName =
		 * file.getOriginalFilename(); System.out.println("fileName>>" +
		 * fileName); File dir = new File(path, fileName);
		 * System.out.println("dir.exists()>>" + dir.exists()); if
		 * (!dir.exists()) { dir.mkdirs(); } System.out.println("dir.exists()>>"
		 * + dir.exists()); // MultipartFile自带的解析方法 file.transferTo(dir); return
		 * fileName;
		 */
	}

	/**
	 * 文件下载功能
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/down")
	public void down(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 模拟文件，myfile.txt为需要下载的文件
		String fileName = Dir.path+"/" + name;
		// 获取输入流
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
		// 设置文件下载头
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		while ((len = bis.read()) != -1) {
			out.write(len);
			out.flush();
		}
		out.close();
	}

	@ResponseBody
	@RequestMapping(value = "/FragileIn")
	public Map FragileIn(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		// 保存数据库的路径
		// 定义文件保存的本地路径
		String localPath = Dir.path+"/";
		// 定义 文件名
		String filename = new Date().getTime() + "";
		// 获得文件类型（可以判断如果不是图片，禁止上传）
		String contentType = file.getContentType();
		// 获得文件后缀名
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// 得到 文件名
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// 文件保存路径
		file.transferTo(new File(localPath + filename));
		Map a = new HashMap<>();
		try {
			Process pro = Runtime.getRuntime()
					.exec(Dir.cmd+" "+Dir.path+"&&python FragileEncode.py " + filename);
			// /calc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
			String msg = null;
			System.out.println("em.。。");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "嵌入成功");
			a.put("name", filename);
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "嵌入失败，请重试");
		}

		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/FragileOut")
	public Map FragileOut(MultipartFile file, HttpServletRequest request) {
		// 保存数据库的路径
		// 定义文件保存的本地路径
		String localPath = Dir.path+"/";
		// 定义 文件名
		String filename = new Date().getTime() + "";
		// 获得文件类型（可以判断如果不是图片，禁止上传）
		String contentType = file.getContentType();
		// 获得文件后缀名
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// 得到 文件名
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// 文件保存路径
		Map a = new HashMap<>();
		StringBuilder result = new StringBuilder();
		try {
			file.transferTo(new File(localPath + filename));
			Process pro = Runtime.getRuntime()
					.exec(Dir.cmd+" "+Dir.path+"&&python FragileDecode.py " + filename);
			try {
				Thread.sleep(1000); // 1000 毫秒，也就是1秒.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			File file2 = new File(""+Dir.path+"/" + filename + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file2));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (IOException exception) {
			System.out.println(exception);
		}
		if ("fail".equals(result.toString().substring(2))) {
			a.put("name", "fail" + filename);
		}
		a.put("text", result.toString().substring(2));
		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/PicIn")
	public Map PicIn(String image1, String image2) {
		Map a = new HashMap<>();
		try {
			Process pro = Runtime.getRuntime().exec(
					Dir.cmd+" "+Dir.path+"&&python bwm.py encode " + image1 + " " + image2 + " ok" + image1);
			// /calc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
			String msg = null;
			System.out.println("em.。。");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "嵌入成功");
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "嵌入失败，请重试");
		}
		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/PicOut")
	public Map PicOut(String image1, String image2) {

		// 文件保存路径
		Map a = new HashMap<>();
	
		try {
			Process pro = Runtime.getRuntime().exec(
					Dir.cmd+" "+Dir.path+"&&python bwm.py decode " + image1 + " " + image2 + " out" + image1);
			// /calc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
			String msg = null;
			System.out.println("em.。。");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "提取成功");
			a.put("name", "out"+image1);
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "提取失败，请重试");
		}
		return a;
	}

}