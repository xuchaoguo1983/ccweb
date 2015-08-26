package com.pengyang.ccweb.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * �������ϴ��ļ���
 * 
 * @author Administrator
 */
public class UploadUtil {
	private final static Logger log = Logger.getLogger(UploadUtil.class);

	private final static String URL_PREFIX = "http://%1$s:%2$s/uploadx?type=%3$s&userid=%4$s&md5=%5$s";
	private final static long MAX_FILE_SIZE = 5 * 1024 * 1024;// 1M

	public static UploadFileResult upload(File file) throws Exception {
		HttpURLConnection url_con = null;
		try {
			if (file.length() > MAX_FILE_SIZE) {
				throw new Exception("�ļ���С���ܳ���5M");
			}

			String ip = "172.16.124.188";// "10.106.200.188";//"172.16.124.188";
											// //"filesupload.cjsc.com.cn";//
											// �ļ�������IP��ַ
			String port = "20001";// �ļ������ϴ��������˿�
			String type = "11"; // ϵͳ���ͺ�20
			String userid = "2451";// �ϴ����û��ţ���Ӧ���û������е��û���

			String file_name = file.getName();
			if (!file_name.contains(".")) {
				file_name += ".jpg";
			}

			log.debug("�ļ�·����" + file.getPath());
			log.debug("�ļ����ƣ�" + file_name);
			log.debug("�ļ���С��" + file.length());
			log.debug("MD5��" + FileMd5Util.MD5(file));
			String md5 = FileMd5Util.MD5(file);
			Long file_length = file.length();

			String urlstr = String.format(URL_PREFIX, ip, port, type, userid,
					md5);

			URL url = new URL(urlstr);
			log.debug("File URL:" + urlstr.toString());

			// ����HTTP����
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setDoOutput(true);
			url_con.setDoInput(true);
			url_con.setRequestProperty("Accept-Charset", "GBK");
			url_con.setRequestProperty("contentType", "GBK");
			url_con.setRequestProperty("Attach-Name",
					java.net.URLEncoder.encode(file_name, "GBK"));
			url_con.setRequestProperty("Content-Length", file_length.toString());
			byte[] buf = new byte[1024];
			int len = 0;
			FileInputStream fis = new FileInputStream(file);
			fis.available();
			OutputStream out = url_con.getOutputStream();

			while ((len = fis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			
			out.flush();
			out.close();
			fis.close();

			InputStream in = url_con.getInputStream();
			StringBuilder tempStr = new StringBuilder();
			while ((len = in.read(buf)) != -1) {
				tempStr.append(new String(buf, 0, len, "GBK"));
			}
			in.close();

			ObjectMapper objectMapper = new ObjectMapper();

			UploadFileResult result = objectMapper.readValue(
					tempStr.toString(), UploadFileResult.class);

			return result;

		} finally {
			if (url_con != null)
				url_con.disconnect();
		}
	}

}
