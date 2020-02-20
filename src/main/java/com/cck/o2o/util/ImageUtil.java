package com.cck.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ch.qos.logback.classic.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");
	private static final Random RANDOM = new Random();
	private static Logger logger = (Logger) LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * 将CommonsMultipartFile转换为File类型
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFile2File(CommonsMultipartFile cFile) {
		//以cFile的文件名创建file
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("D:/o2o_resources/image/hollowKnight.jpg"))
		.size(800, 400).watermark(Positions.BOTTOM_RIGHT, 
				ImageIO.read(new File(basePath + "/freeExpress.jpg")), 0.2f).outputQuality(0.8f)
		.toFile("D:/o2o_resources/image/hollowKnightNew.jpg");
	}

	/**
	 * 处理缩略图，并返回生成的缩略图的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
		//利用随机名生成用户图片名称
		String realFileName = getRandomFileName();
		//获取用户图片的扩展名
		String extension = getFileExtension(fileName);
		//根据相对路径，创建目标路径的目录
		makeDirPath(targetAddr);
		//生成相对路径
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is " + relativeAddr);
		//在绝对路径下创建图片
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current completeAddr is " + PathUtil.getImgBasePath() + relativeAddr);
		try {
			//获取用户输入流
			//将大小调整为200*200
			//水印图置于右下角，0.25的透明度
			//水印图的路径在项目的classpath中，即basePath/freeExpress.jpg
			//输出质量为0.8，输出到dest文件中
			Thumbnails.of(thumbnailInputStream).size(400, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/freeExpress.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		//返回相对路径以解耦系统
		return relativeAddr;
	}
	/**
	 * 创建目标路径所涉及到的目录
	 * 例：
	 * D:/o2o_resources/image/xxx.jpg是目标路径
	 * 那么，o2o_resources image 等目录必须自动创建出来
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		//获取绝对目标路径
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		//生成目录
		File dirFile = new File(realFileParentPath);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}	
	}
	/**
	 * 获取文件扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		//返回原文件名中最后一个.之后的字符串
		return fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 生成随机文件名，格式为：年月日时分秒+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机的五位数
		int randNum = RANDOM.nextInt(90000) + 10000;
		//返回当前的时间
		String nowTimeDate = SIMPLE_DATE_FORMAT.format(new Date());
		return nowTimeDate + randNum;
	}
}
