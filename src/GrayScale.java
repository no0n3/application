import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class GrayScale {

	public static void main(String[] args) throws IOException {
		convertToGreyscale("C:\\Users\\USER\\Desktop\\john-romero.jpg");
	}

	private static void convertToGreyscale(String imgPath) throws IOException {
		if (null != imgPath) {
			File pic = new File(imgPath);

			if (pic.exists()) {
				BufferedImage bufferedImage = ImageIO.read(pic);

				alterPixels(bufferedImage);

				ImageIO.write(bufferedImage, getImgType(imgPath), Files
						.newOutputStream(Paths.get(grayscaleImgName(imgPath))));
			}
		}
	}

	private static void alterPixels(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				int pixel = image.getRGB(y, x);

				int red = (pixel >> 16) & 0x0000FF;
				int green = (pixel >> 8) & 0x0000FF;
				int blue = (pixel) & 0x0000FF;

				pixel = (red + blue + green) / 3;

				pixel = (pixel & 0xff) + ((pixel & 0xff) << 8)
						+ ((pixel & 0xff) << 16);

				image.setRGB(y, x, pixel);
			}
		}
	}

	private static String getImgType(String imgPath) {
		imgPath = imgPath.toLowerCase();
		if (imgPath.endsWith(".jpg")) {
			return "jpg";
		} else if (imgPath.endsWith(".png")) {
			return "png";
		} else if (imgPath.endsWith(".bmp")) {
			return "bmp";
		} else {
			throw new RuntimeException("Invalid image format.");
		}
	}

	private static String grayscaleImgName(String imgPath) {
		StringBuilder sb = new StringBuilder(imgPath);
		sb.insert(sb.lastIndexOf("."), "_grayscale");
		return sb.toString();
	}
}