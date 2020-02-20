package com.cck.o2o.service;

import java.io.InputStream;

import com.cck.o2o.dto.ShopExecution;
import com.cck.o2o.entity.Shop;

public interface ShopService {
	/**
	 * 增加店铺
	 * @param shop 店铺对象
	 * @param shopImg 图片信息
	 * @return 执行结果
	 */
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName);
}
