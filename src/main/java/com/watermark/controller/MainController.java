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
 * ��������
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
			// /calc"��calc����Ҫִ�д򿪼�������������ùػ����Լ�����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // ��Ȼcmd�������ֱ�����������ͨ��IO���������Ա�֤�����ݽ���һ�����塣
			String msg = null;
			System.out.println("em.����");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "Ƕ��ɹ�");
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "Ƕ��ʧ�ܣ�������");
		}

		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/TextOut")
	public Map TextOut(MultipartFile file, HttpServletRequest request) {
		// �������ݿ��·��
		// �����ļ�����ı���·��
		String localPath = Dir.path+"/";
		// ���� �ļ���
		String filename = new Date().getTime() + "";
		// ����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���
		String contentType = file.getContentType();
		// ����ļ���׺��
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// �õ� �ļ���
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// �ļ�����·��
		Map a = new HashMap<>();
		StringBuilder result = new StringBuilder();
		try {
			file.transferTo(new File(localPath + filename));
			Process pro = Runtime.getRuntime().exec(Dir.cmd+" "+Dir.path+"&&python TextOut.py " + filename);
			try {
				Thread.sleep(1000); // 1000 ���룬Ҳ����1��.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			File file2 = new File(""+Dir.path+"/" + filename + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file2));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
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
		// �������ݿ��·��
		// �����ļ�����ı���·��
		String localPath = Dir.path+"/";
		// ���� �ļ���
		String filename = new Date().getTime() + "";
		// ����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���
		String contentType = file.getContentType();
		// ����ļ���׺��
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// �õ� �ļ���
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// �ļ�����·��
		file.transferTo(new File(localPath + filename));
		return filename;
		/*
		 * System.out.println("comming!"); String path = ""+Dir.path+"";
		 * System.out.println("path>>" + path); String fileName =
		 * file.getOriginalFilename(); System.out.println("fileName>>" +
		 * fileName); File dir = new File(path, fileName);
		 * System.out.println("dir.exists()>>" + dir.exists()); if
		 * (!dir.exists()) { dir.mkdirs(); } System.out.println("dir.exists()>>"
		 * + dir.exists()); // MultipartFile�Դ��Ľ������� file.transferTo(dir); return
		 * fileName;
		 */
	}

	/**
	 * �ļ����ع���
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/down")
	public void down(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ģ���ļ���myfile.txtΪ��Ҫ���ص��ļ�
		String fileName = Dir.path+"/" + name;
		// ��ȡ������
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
		// �����ļ�����ͷ
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		// 1.�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ�����
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
		// �������ݿ��·��
		// �����ļ�����ı���·��
		String localPath = Dir.path+"/";
		// ���� �ļ���
		String filename = new Date().getTime() + "";
		// ����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���
		String contentType = file.getContentType();
		// ����ļ���׺��
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// �õ� �ļ���
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// �ļ�����·��
		file.transferTo(new File(localPath + filename));
		Map a = new HashMap<>();
		try {
			Process pro = Runtime.getRuntime()
					.exec(Dir.cmd+" "+Dir.path+"&&python FragileEncode.py " + filename);
			// /calc"��calc����Ҫִ�д򿪼�������������ùػ����Լ�����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // ��Ȼcmd�������ֱ�����������ͨ��IO���������Ա�֤�����ݽ���һ�����塣
			String msg = null;
			System.out.println("em.����");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "Ƕ��ɹ�");
			a.put("name", filename);
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "Ƕ��ʧ�ܣ�������");
		}

		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/FragileOut")
	public Map FragileOut(MultipartFile file, HttpServletRequest request) {
		// �������ݿ��·��
		// �����ļ�����ı���·��
		String localPath = Dir.path+"/";
		// ���� �ļ���
		String filename = new Date().getTime() + "";
		// ����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���
		String contentType = file.getContentType();
		// ����ļ���׺��
		String suffixName = contentType.substring(contentType.indexOf("/") + 1);
		// �õ� �ļ���
		filename = filename + "." + suffixName;
		System.out.println(filename);
		// �ļ�����·��
		Map a = new HashMap<>();
		StringBuilder result = new StringBuilder();
		try {
			file.transferTo(new File(localPath + filename));
			Process pro = Runtime.getRuntime()
					.exec(Dir.cmd+" "+Dir.path+"&&python FragileDecode.py " + filename);
			try {
				Thread.sleep(1000); // 1000 ���룬Ҳ����1��.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			File file2 = new File(""+Dir.path+"/" + filename + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file2));// ����һ��BufferedReader������ȡ�ļ�
			String s = null;
			while ((s = br.readLine()) != null) {// ʹ��readLine������һ�ζ�һ��
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
			// /calc"��calc����Ҫִ�д򿪼�������������ùػ����Լ�����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // ��Ȼcmd�������ֱ�����������ͨ��IO���������Ա�֤�����ݽ���һ�����塣
			String msg = null;
			System.out.println("em.����");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "Ƕ��ɹ�");
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "Ƕ��ʧ�ܣ�������");
		}
		return a;
	}

	@ResponseBody
	@RequestMapping(value = "/PicOut")
	public Map PicOut(String image1, String image2) {

		// �ļ�����·��
		Map a = new HashMap<>();
	
		try {
			Process pro = Runtime.getRuntime().exec(
					Dir.cmd+" "+Dir.path+"&&python bwm.py decode " + image1 + " " + image2 + " out" + image1);
			// /calc"��calc����Ҫִ�д򿪼�������������ùػ����Լ�����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); // ��Ȼcmd�������ֱ�����������ͨ��IO���������Ա�֤�����ݽ���һ�����塣
			String msg = null;
			System.out.println("em.����");
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}
			a.put("answer", "��ȡ�ɹ�");
			a.put("name", "out"+image1);
		} catch (IOException exception) {
			System.out.println(exception);
			a.put("answer", "��ȡʧ�ܣ�������");
		}
		return a;
	}

}