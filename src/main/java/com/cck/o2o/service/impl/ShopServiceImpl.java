package com.cck.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cck.o2o.dao.ShopDao;
import com.cck.o2o.dto.ShopExecution;
import com.cck.o2o.entity.Shop;
import com.cck.o2o.enums.ShopStateEnum;
import com.cck.o2o.exceptions.ShopOperationException;
import com.cck.o2o.service.ShopService;
import com.cck.o2o.util.ImageUtil;
import com.cck.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		//检查传入的参数是否合法
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//初始化店铺状态为审核中
			shop.setEnableStatus(ShopStateEnum.CHECK.getState());
			shop.setCreateTime(new Date());
			shop.setUpdateTime(new Date());
			//1：向数据库插入数据
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum <= 0) {
				//只有抛出ShopOperationException，才能够回滚
				//Exception无法回滚事务
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImg != null) {
					//2：存储图片到对应的目录
					try {
						//将图片存储地址保存至shop的shopImg中
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						//对象存储不成功则抛出异常，回滚事务
						throw new ShopOperationException("addShopImg error: " + e.getMessage());
					}
					//3：将新的图片地址更新到数据库中
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0) {
						//数据库存储不成功则抛出异常，回滚事务
						throw new ShopOperationException("店铺图片更新至数据库失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error: " + e.getMessage());
		}
		//更新成功后，返回ShopExecution中CHECK “审核中” 的执行结果
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}
	private void addShopImg(Shop shop, File shopImg) {
		//获取图片的存储相对路径
		String dest = PathUtil.getShopImgPath(shop.getShopId());
		//生成加水印的缩略图，并获得缩略图的相对路径
		String shopImgRelAddr = ImageUtil.generateThumbnail(shopImg, dest);
		//更新缩略图相对地址到shop对象
		shop.setShopImg(shopImgRelAddr);
	}

}
