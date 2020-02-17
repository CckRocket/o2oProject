package com.cck.o2o.util;

public class PathUtil {
	private static String separator = System.getProperty("file.separator");
	/**
	 * 
	 * @return 返回项目图片根路径
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/o2o_resources/image/";
		}else {
			basePath = "home/o2o_resources/image/";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	/**
	 * 
	 * @param shopId
	 * @return 项目图片子路径
	 */
	public static String getShopImgPath(long shopId) {
		String imgPath = "upload/item/shop/" + shopId + "/";
		return imgPath.replace("/", separator);
	}
}
