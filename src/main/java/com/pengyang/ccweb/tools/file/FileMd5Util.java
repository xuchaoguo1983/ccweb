package com.pengyang.ccweb.tools.file;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Calendar;

public class FileMd5Util {
	public static String getDate(){
		Calendar ca = Calendar.getInstance();
        int year=ca.get(Calendar.YEAR);//��ȡ���
        int month=ca.get(Calendar.MONTH);//��ȡ�·�
        int day=ca.get(Calendar.DATE);//��ȡ��
        int date=year*10000+(month+1)*100+day;
        //�·���0-11 ��������Ҫ��1
        return Integer.toString(date);
	}
	
	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
		   '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	 public final static String MD5(String s) {
		  try {
		   byte[] strTemp = s.getBytes();
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		   mdTemp.update(strTemp);
		   byte[] md = mdTemp.digest();
		   int j = md.length;
		   char str[] = new char[j * 2];
		   int k = 0;
		   for (int i = 0; i < j; i++) {
		    byte byte0 = md[i];
		    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
		    str[k++] = hexDigits[byte0 & 0xf];
		   }
		   return new String(str);
		  } catch (Exception e) {
		   return null;
		  }
	}
	 public final static String MD5(File file) {
		  
		  FileInputStream in = null;
		  byte buffer[] = new byte[1024];
		  int len;
		  try {
		   MessageDigest digest = MessageDigest.getInstance("MD5");
		   in = new FileInputStream(file);
		   while ((len = in.read(buffer, 0, 1024)) != -1) {
		    digest.update(buffer, 0, len);
		   }
		   in.close();
		   byte[] b = digest.digest();
		   return byteToHexString(b).toLowerCase();

		  } catch (Exception e) {
		      e.printStackTrace();
		   return null;
		  }
	}
	private static String byteToHexString(byte[] tmp) {
		  String s;
		  // ���ֽڱ�ʾ���� 16 ���ֽ�
		  char str[] = new char[16 * 2]; // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ���
		  // ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
		  int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
		  for (int i = 0; i < 16; i++) { // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
		   // ת���� 16 �����ַ���ת��
		   byte byte0 = tmp[i]; // ȡ�� i ���ֽ�
		   str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ��и� 4 λ������ת��, 
		   // >>> Ϊ�߼����ƣ�������λһ������
		   str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ��е� 4 λ������ת��
		  }
		  s = new String(str); // ����Ľ��ת��Ϊ�ַ���
		  return s;
	}
	
	 public final static String MD5(InputStream file) {
		  
		 InputStream in = null;
		  byte buffer[] = new byte[1024];
		  int len;
		  try {
		   MessageDigest digest = MessageDigest.getInstance("MD5");
		   in = (file);
		   while ((len = in.read(buffer, 0, 1024)) != -1) {
		    digest.update(buffer, 0, len);
		   }
		   in.close();
		   byte[] b = digest.digest();
		   return byteToHexString(b).toLowerCase();

		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  }
	}
}

