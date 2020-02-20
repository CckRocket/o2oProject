package com.cck.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cck.o2o.dto.ShopExecution;
import com.cck.o2o.entity.Area;
import com.cck.o2o.entity.PersonInfo;
import com.cck.o2o.entity.Shop;
import com.cck.o2o.entity.ShopCategory;
import com.cck.o2o.enums.ShopStateEnum;
import com.cck.o2o.exceptions.ShopOperationException;
import com.cck.o2o.service.AreaService;
import com.cck.o2o.service.ShopCategoryService;
import com.cck.o2o.service.ShopService;
import com.cck.o2o.util.CodeUtil;
import com.cck.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo(){
		//ResonseBody能够将返回结果自动转换为json对象
		//modelMap存储处理结果
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//接收后端发送的shopCategory列表信息
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		//接收后端发送的Area列表信息
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			//执行是否出错的信息全部放在modelMap中
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	//ResponseBody将表单自动转换为json对象
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//首先检查验证码是否输入无误
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//Step 1 接收并转化参数，包括店铺信息和图片信息
		//将request中shopStr部分解析出来
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			//将解析出来的shopStr字符串转换为shop对象
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		//从request中get会话的servlet上下文
		CommonsMultipartResolver comMultiResolver 
		= new CommonsMultipartResolver(request.getSession().getServletContext());
		//如果文件解析器得到的request中有文件上传
		if(comMultiResolver.isMultipart(request)) {
			//将request强转为multipartHttpServletRequest
			MultipartHttpServletRequest multipartHttpServletRequest 
			= (MultipartHttpServletRequest) request;
			//从请求中解析出前端传过来的shopImg的文件流
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传文件不能为空");
			return modelMap;
		}
		//Step 2 注册店铺
		if(shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			//Session TODO
			owner.setUserId(1L);
			shop.setOwner(owner);
			//执行添加店铺和缩略图的操作
			ShopExecution shopExecution;
			try {
				//将输入流和原文件名传入以添加店铺
				shopExecution = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
				if(shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				}else {
					//添加失败则返回执行结果的注释
					modelMap.put("success", false);
					modelMap.put("errMsg", shopExecution.getStateInfo());
				}
			}catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺信息不能为空");
			return modelMap;
		}
		//Step 3 返回结果
	}

	//	private static void inputStream2File(InputStream input, File file) {
	//		FileOutputStream fos = null;
	//		try {
	//			//将输出流绑定到文件上
	//			fos = new FileOutputStream(file);
	//			//buffer从输入流中接收数据
	//			byte[] buffer = new byte[1024];
	//			//readResult判断是否读到input的末尾，是则为-1，不是则返回实际读取长度
	//			int readResult = 0;
	//			//只要没有读到文件末尾，就一直向fos中写数据
	//			while((readResult = input.read(buffer)) != -1) {
	//				fos.write(buffer, 0, readResult);
	//			}
	//		} catch (Exception e) {
	//			throw new RuntimeException("inputStream2File写入文件异常" + e.getMessage());
	//			//不论是否写入成功，IO流都要关闭，所以用finally语句块
	//		}finally {
	//			try {
	//				if(fos != null) fos.close();
	//				if(input != null) input.close();
	//			} catch (IOException e) {
	//				throw new RuntimeException("inputStream2File关闭IO异常" + e.getMessage());
	//			}
	//		}
	//	}
}
