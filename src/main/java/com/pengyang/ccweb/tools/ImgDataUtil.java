package com.pengyang.ccweb.tools;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.pengyang.ccweb.tools.file.UploadFileResult;
import com.pengyang.ccweb.tools.file.UploadUtil;

public class ImgDataUtil {
	private final static String IMG_DATA_PREFIX = "<img src=\"";
	private final static String IMG_DATA_SUFFIX = "\"";

	private final static String IMG_FILE_NAME_PREFIX = "data-filename=\"";
	private final static String MG_FILE_NAME_SUFFIX = "\"";

	/**
	 * 将包含Base64编码的图片信息的Html源转换为存储到文件服务器的图片文件地址
	 * 
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public static String parse(String html, String imagePath) {
		int beginIndex = html.indexOf(IMG_DATA_PREFIX);
		if (beginIndex >= 0) {
			beginIndex += IMG_DATA_PREFIX.length();
			int endIndex = html.indexOf(IMG_DATA_SUFFIX, beginIndex);
			if (endIndex > 0) {
				String imgDataStr = html.substring(beginIndex, endIndex);

				// get file name
				int nameBeginIndex = html.indexOf(IMG_FILE_NAME_PREFIX,
						endIndex) + IMG_FILE_NAME_PREFIX.length();
				int nameEndIndex = html.indexOf(MG_FILE_NAME_SUFFIX,
						nameBeginIndex);
				String imgFileName = html.substring(nameBeginIndex,
						nameEndIndex);

				imgFileName = String.format("%s-%s",
						System.currentTimeMillis(), imgFileName);

				int index = imgDataStr.indexOf("base64");
				if (index > 0) {
					byte[] imageByte = Base64.decodeBase64(imgDataStr
							.substring(index + 7));

					final File f = new File(imagePath, imgFileName);

					try {
						FileUtils.writeByteArrayToFile(f, imageByte);

						UploadFileResult result = UploadUtil.upload(f);
						if (result.getError_no() == 0) {
							String newHtml = html.replace(imgDataStr,
									result.getUrl());
							return parse(newHtml, imagePath);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return html;
	}
}
