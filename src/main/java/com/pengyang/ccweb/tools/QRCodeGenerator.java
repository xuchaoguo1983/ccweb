package com.pengyang.ccweb.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {
	private static final Logger log = Logger.getLogger(QRCodeGenerator.class);

	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static File generate(String assetsName, String params, String path,
			int width, int height) throws Exception {

		log.info("QRCodeGenerator-->start to generate qrcode.");
		log.info("the qrcode's save path is:" + path);

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		// ¶þÎ¬ÂëÍ¼Æ¬Ãû³Æ
		String fileName = assetsName.concat("_")
				.concat(String.valueOf(System.currentTimeMillis()))
				.concat(".png");

		// ¶þÎ¬ÂëÍ¼Æ¬´æ·ÅÂ·¾¶
		path = path.concat(fileName);

		file = new File(path);

		if (!file.exists()) {
			file.createNewFile();
		}

		Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(
				EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 2); /* default = 4 */

		BitMatrix bitMatrix = new MultiFormatWriter().encode(params,
				BarcodeFormat.QR_CODE, width, height, hints);

		writeToFile(bitMatrix, "png", file);

		log.info("GenerateQRCode-->end to generate qrcode.");

		return file;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws Exception {

		BufferedImage image = toBufferedImage(matrix);

		ImageIO.write(image, format, file);

	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();

		int height = matrix.getHeight();

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < width; x++) {

			for (int y = 0; y < height; y++) {

				image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);

			}

		}

		return image;

	}
}
