package com.cck.o2o.exceptions;

public class ShopOperationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6195941553834378777L;

	public ShopOperationException(String msg) {
		//利用父类RuntimeException的构造函数构造ShopOperationException
		super(msg);
	}
}
