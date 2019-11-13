package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	ProductDao productDao;
	
	///Constructor
	public ProductServiceImpl() {
		System.out.println(":: "+this.getClass()+" default 생성자 호출...");
	}
	
	public void setProductDao(ProductDao productDao) {
		System.out.println(":: "+getClass()+".setProductDao 생성자 호출...");
		this.productDao = productDao;
	}

	@Override
	public Product findProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		List<Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		productDao.insertProduct(product);

	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);

	}

}
