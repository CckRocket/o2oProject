package com.cck.o2o.entity;

import java.util.Date;

public class ProductImg {
	private Long productImgId;
	private Integer priority;
	private Long productId;
	private String productImgAddr;
	private String productImgDesc;
	private Date createTime;
	public Long getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductImgAddr() {
		return productImgAddr;
	}
	public void setProductImgAddr(String productImgAddr) {
		this.productImgAddr = productImgAddr;
	}
	public String getProductImgDesc() {
		return productImgDesc;
	}
	public void setProductImgDesc(String productImgDesc) {
		this.productImgDesc = productImgDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
