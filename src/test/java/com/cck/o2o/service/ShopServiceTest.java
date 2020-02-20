package com.cck.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cck.o2o.BaseTest;
import com.cck.o2o.dto.ShopExecution;
import com.cck.o2o.entity.Area;
import com.cck.o2o.entity.PersonInfo;
import com.cck.o2o.entity.Shop;
import com.cck.o2o.entity.ShopCategory;
import com.cck.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		Area area = new Area();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(1);
		owner.setUserId(1L);
		shopCategory.setShopCategoryId(1L);
		shop.setAdvice("testing");
		shop.setArea(area);
		shop.setCreateTime(new Date());
		//将状态设置为审核中，利用getState获取审核中对应的字符
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setOwner(owner);
		shop.setPriority(1);
		shop.setShopCategory(shopCategory);
		shop.setShopDesc("test");
		shop.setShopName("test shop Service");
		shop.setShopPhone("test");
		File shopImg = new File("D:/o2o_resources/image/hollowKnight.jpg");
		InputStream shopImgInputStream = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop, shopImgInputStream,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
}
