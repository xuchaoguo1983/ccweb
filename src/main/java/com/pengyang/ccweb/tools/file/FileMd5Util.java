package com.pengyang.ccweb.tools.file;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Calendar;

public class FileMd5Util {
	public static String getDate(){
		Calendar ca = Calendar.getInstance();
        int year=ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        int date=year*10000+(month+1)*100+day;
        //月份是0-11 所以这里要加1
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
		  // 用字节表示就是 16 个字节
		  char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		  // 所以表示成 16 进制需要 32 个字符
		  int k = 0; // 表示转换结果中对应的字符位置
		  for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
		   // 转换成 16 进制字符的转换
		   byte byte0 = tmp[i]; // 取第 i 个字节
		   str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
		   // >>> 为逻辑右移，将符号位一起右移
		   str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		  }
		  s = new String(str); // 换后的结果转换为字符串
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

