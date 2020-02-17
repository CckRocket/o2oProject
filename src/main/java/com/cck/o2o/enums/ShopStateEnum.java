package com.cck.o2o.enums;

public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),
	SUCCESS(1,"操作成功"),PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"shopId为空"),
	NULL_SHOP(-1003,"shop信息为空");
	
	private int state;
	private String stateInfo;
	//不能让外界调用方法，因此是private
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	/**
	 * 根据传入的state返回enum对象
	 */
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
	//仅允许getter，禁止setter
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
}
