package com.cck.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cck.o2o.BaseTest;
import com.cck.o2o.entity.Area;
import com.cck.o2o.entity.PersonInfo;
import com.cck.o2o.entity.Shop;
import com.cck.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		Area area = new Area();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(1);
		owner.setUserId(1L);
		shopCategory.setShopCategoryId(1L);
		
		shop.setAdvice("test");
		shop.setArea(area);
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setOwner(owner);
		shop.setPriority(1);
		shop.setShopAddr("test");
		shop.setShopCategory(shopCategory);
		shop.setShopDesc("test");
		shop.setShopImg("test");
		shop.setShopName("test shop");
		shop.setShopPhone("test");
		
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试更新的描述");
		shop.setUpdateTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}
