package com.cck.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
	@RequestMapping(value = "/shopoperation")
	//spring-web.xml中的viewResolver负责前后缀，这里只提供中间路径即可
	public String shopOperation() {
		return "shop/shopoperation";
	}
}
