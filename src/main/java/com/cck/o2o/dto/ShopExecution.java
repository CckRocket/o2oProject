package com.cck.o2o.dto;

import java.util.List;

import com.cck.o2o.entity.Shop;
import com.cck.o2o.enums.ShopStateEnum;

public class ShopExecution {
	//存储新增店铺、更新店铺的结果状态：成功or失败
	private int state;
	//状态的标识，用作state的解释
	private String stateInfo;
	//店铺数量
	private int count;
	//增删改查店铺时返回的店铺对象
	private Shop shop;
	//查询店铺列表时返回的店铺列表对象
	private List<Shop> shopList;
	public ShopExecution() {
	}
	/**
	 * 增删改查失败时返回状态
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	/**
	 * 增删改查成功时返回单个shop对象
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum, Shop shop){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	/**
	 * 操作店铺列表成功时返回列表
	 * @param stateEnum
	 * @param shopList
	 */
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
}
